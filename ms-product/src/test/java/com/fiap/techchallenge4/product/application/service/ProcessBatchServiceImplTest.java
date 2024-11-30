package com.fiap.techchallenge4.product.application.service;

import com.fiap.techchallenge4.product.application.exception.NotFoundException;
import com.fiap.techchallenge4.product.application.exception.ValidationException;
import com.fiap.techchallenge4.product.application.service.impl.ProcessBatchServiceImpl;
import com.fiap.techchallenge4.product.core.enums.StatusCsv;
import com.fiap.techchallenge4.product.core.model.CsvLoader;
import com.fiap.techchallenge4.product.core.model.LogError;
import com.fiap.techchallenge4.product.infrasctructure.utils.FileManipulationUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessBatchServiceImplTest {

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private Job productJob;

    @Mock
    private CsvLoaderService csvLoaderService;

    @Mock
    private LogErrorService logErrorService;

    @InjectMocks
    private ProcessBatchServiceImpl processBatchService;

    @Spy
    private FileManipulationUtils fileManipulationUtils;

    @BeforeEach
    void setup() throws IOException {
        processBatchService = new ProcessBatchServiceImpl(jobLauncher, productJob, csvLoaderService, logErrorService);
        Files.createDirectories(Path.of("src/test/resources/files"));
        Files.createDirectories(Path.of("src/test/resources/moved"));
    }

    @AfterAll
    static void deleteArchives() throws IOException {
        Files.deleteIfExists(Path.of("src/test/resources/files"));
        Files.deleteIfExists(Path.of("src/test/resources/moved"));
    }


    @Test
    void execute_deveLancarExcecaoQuandoNaoHouverArquivosWaiting() {
        // Arrange
        when(csvLoaderService.findAllByStatusWaiting()).thenReturn(Collections.emptyList());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, processBatchService::execute);
        assertEquals("Nenhum arquivo CSV com status 'WAITING' encontrado para processamento.", exception.getMessage());
        verify(csvLoaderService, times(1)).findAllByStatusWaiting();
    }

    @Test
    void processBatch_deveAtualizarStatusParaFinishedComSucesso() throws Exception {
        // Arrange
        CsvLoader csvLoader = new CsvLoader();
        csvLoader.setId(1L);
        csvLoader.setFileName("file1.csv");
        csvLoader.setStatusCsv(StatusCsv.WAITING);

        // Mock da execução do job
        when(jobLauncher.run(eq(productJob), any(JobParameters.class))).thenReturn(mock(JobExecution.class));

        // Mock do método estático moveFile, simulando sucesso
        try (MockedStatic<FileManipulationUtils> mockedStatic = mockStatic(FileManipulationUtils.class)) {
            mockedStatic.when(() -> FileManipulationUtils.moveFile("src/test/resources/files","src/test/resources/moved", eq("file1.csv")))
                    .thenAnswer(invocation -> null);
                      // Nenhum erro é gerado, simula sucesso

            // Act
            processBatchService.processBatch(csvLoader);

            // Assert
            verify(csvLoaderService, times(1)).save(csvLoader);
            assertEquals(StatusCsv.FINISHED, csvLoader.getStatusCsv());

            // Verifica se o método moveFile foi chamado uma vez com os parâmetros corretos
            mockedStatic.verify(() -> FileManipulationUtils.moveFile(anyString(), anyString(), eq("file1.csv")), times(1));
        }
    }

    @Test
    void processBatch_deveSalvarErroEAtualizarStatusParaError() throws Exception {
        // Arrange
        CsvLoader csvLoader = new CsvLoader();
        csvLoader.setId(1L);
        csvLoader.setFileName("file1.csv");
        csvLoader.setStatusCsv(StatusCsv.WAITING);

        Exception exception = new RuntimeException("Erro ao processar o arquivo");
        when(jobLauncher.run(eq(productJob), any(JobParameters.class))).thenThrow(exception);

        // Act & Assert
        ValidationException validationException = assertThrows(
                ValidationException.class,
                () -> processBatchService.processBatch(csvLoader)
        );

        assertEquals("Erro durante o processamento do arquivo CSV " + exception.getMessage(), validationException.getMessage());
        verify(csvLoaderService, times(1)).save(csvLoader);
        assertEquals(StatusCsv.ERROR, csvLoader.getStatusCsv());

        ArgumentCaptor<LogError> captor = ArgumentCaptor.forClass(LogError.class);
        verify(logErrorService, times(1)).save(captor.capture());
        LogError logError = captor.getValue();
        assertEquals("Erro ao processar o arquivo CSV 'file1.csv': Erro ao processar o arquivo", logError.getError());
    }

    @Test
    void processBatch_deveLancarErroQuandoFalharAoMoverArquivo() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        // Arrange
        CsvLoader csvLoader = new CsvLoader();
        csvLoader.setId(1L);
        csvLoader.setFileName("file1.csv");
        csvLoader.setStatusCsv(StatusCsv.WAITING);

        // Mock da execução do Job
        when(jobLauncher.run(eq(productJob), any(JobParameters.class))).thenReturn(mock(JobExecution.class));

        // Mock da falha ao mover o arquivo
        try (MockedStatic<FileManipulationUtils> mockedStatic = Mockito.mockStatic(FileManipulationUtils.class)) {
            mockedStatic.when(() -> FileManipulationUtils.moveFile(anyString(), anyString(), eq("file1.csv")))
                    .thenThrow(IOException.class);

            // Act & Assert
            IOException validationException = assertThrows(
                    IOException.class,
                    () -> processBatchService.processBatch(csvLoader)
            );

            // Assert: Verificar mensagem e status atualizado
            assertEquals("Erro durante o processamento do arquivo CSV", validationException.getMessage());
            verify(csvLoaderService, times(1)).save(csvLoader);
            assertEquals(StatusCsv.ERROR, csvLoader.getStatusCsv());

            // Verificar o log de erro salvo
            ArgumentCaptor<LogError> captor = ArgumentCaptor.forClass(LogError.class);
            verify(logErrorService, times(1)).save(captor.capture());
            LogError logError = captor.getValue();
            assertEquals("Erro ao processar o arquivo CSV 'file1.csv': Erro ao mover arquivo", logError.getError());
        }
    }

    private void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }
        directory.delete();
    }
}
