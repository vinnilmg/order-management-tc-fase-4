package com.fiap.techchallenge4.product.application.service;

import com.fiap.techchallenge4.product.application.exception.NotFoundException;
import com.fiap.techchallenge4.product.application.exception.ValidationException;
import com.fiap.techchallenge4.product.application.service.impl.ProcessBatchServiceImpl;
import com.fiap.techchallenge4.product.core.enums.StatusCsv;
import com.fiap.techchallenge4.product.core.model.CsvLoader;
import com.fiap.techchallenge4.product.core.model.LogError;
import com.fiap.techchallenge4.product.infrasctructure.utils.FileManipulationUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
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

    private MockedStatic<FileManipulationUtils> mockedStatic;

    @BeforeEach
    void setup() throws IOException {
        // Criar diretórios simulados para os testes
        Files.createDirectories(Path.of("src/test/resources/pending"));
        Files.createDirectories(Path.of("src/test/resources/waiting"));
        Files.createDirectories(Path.of("src/test/resources/moved"));
    }

    @AfterEach
    void tearDown() {
        if (mockedStatic != null) {
            mockedStatic.close();
            mockedStatic = null;
        }
    }

    @Test
    void execute_deveLancarExcecaoQuandoNaoHouverArquivosWaiting() {
        when(csvLoaderService.findAllByStatusWaiting()).thenReturn(Collections.emptyList());

        NotFoundException exception = assertThrows(NotFoundException.class, processBatchService::execute);
        assertEquals("Nenhum arquivo CSV com status 'WAITING' encontrado para processamento.", exception.getMessage());
        verify(csvLoaderService, times(1)).findAllByStatusWaiting();
    }

    @Test
    void processBatch_deveAtualizarStatusParaFinishedComSucesso() throws Exception {
        // Configurar cenário: arquivo CSV válido
        CsvLoader csvLoader = new CsvLoader();
        csvLoader.setId(1L);
        csvLoader.setFileName("file1.csv");
        csvLoader.setStatusCsv(StatusCsv.WAITING);

        // Simular execução do job
        when(jobLauncher.run(eq(productJob), any(JobParameters.class))).thenReturn(mock(JobExecution.class));

        // Simular movimentação de arquivo
        mockedStatic = mockStatic(FileManipulationUtils.class);
        mockedStatic.when(() -> FileManipulationUtils.moveFile(
                "src/test/resources/pending",
                "src/test/resources/waiting",
                "file1.csv"
        )).thenAnswer(invocation -> null);

        // Executar método
        processBatchService.processBatch(csvLoader);

        // Verificar status atualizado e interação com CsvLoaderService
        verify(csvLoaderService, times(1)).save(csvLoader);
        assertEquals(StatusCsv.FINISHED, csvLoader.getStatusCsv());
    }

    @Test
    void processBatch_deveSalvarErroEAtualizarStatusParaError() throws Exception {
        // Configurar cenário: erro ao processar arquivo
        CsvLoader csvLoader = new CsvLoader();
        csvLoader.setId(1L);
        csvLoader.setFileName("file1.csv");
        csvLoader.setStatusCsv(StatusCsv.WAITING);

        // Simular erro ao executar job
        Exception exception = new RuntimeException("Erro ao processar o arquivo");
        when(jobLauncher.run(eq(productJob), any(JobParameters.class))).thenThrow(exception);

        // Executar e validar exceção
        ValidationException validationException = assertThrows(
                ValidationException.class,
                () -> processBatchService.processBatch(csvLoader)
        );

        assertEquals("Erro durante o processamento do arquivo CSV Erro ao processar o arquivo", validationException.getMessage());

        // Verificar status atualizado e salvamento de erro
        verify(csvLoaderService, times(1)).save(csvLoader);
        assertEquals(StatusCsv.ERROR, csvLoader.getStatusCsv());

        // Verificar registro do erro
        ArgumentCaptor<LogError> captor = ArgumentCaptor.forClass(LogError.class);
        verify(logErrorService, times(1)).save(captor.capture());
        LogError logError = captor.getValue();
        assertEquals("Erro ao processar o arquivo CSV 'file1.csv': Erro ao processar o arquivo", logError.getError());
    }

    @Test
    void processBatch_deveLancarErroQuandoFalharAoMoverArquivo() throws Exception {
        // Configurar cenário
        CsvLoader csvLoader = new CsvLoader();
        csvLoader.setId(1L);
        csvLoader.setFileName("file1.csv");
        csvLoader.setStatusCsv(StatusCsv.WAITING);

        // Simular execução do job
        when(jobLauncher.run(eq(productJob), any(JobParameters.class))).thenReturn(mock(JobExecution.class));

        // Simular falha ao mover arquivo
        mockedStatic = mockStatic(FileManipulationUtils.class);
        mockedStatic.when(() -> FileManipulationUtils.moveFile(
                "src/test/resources/pending",
                "src/test/resources/waiting",
                "file1.csv"
        )).thenThrow(new IOException("Erro ao mover o arquivo"));

        // Executar e validar exceção
        ValidationException exception = assertThrows(ValidationException.class, () -> processBatchService.processBatch(csvLoader));
        assertTrue(exception.getMessage().contains("Erro durante o processamento do arquivo CSV"));

        // Verificar status atualizado e salvamento de erro
        verify(csvLoaderService, times(1)).save(csvLoader);
        assertEquals(StatusCsv.ERROR, csvLoader.getStatusCsv());

        // Verificar registro do erro
        ArgumentCaptor<LogError> captor = ArgumentCaptor.forClass(LogError.class);
        verify(logErrorService, times(1)).save(captor.capture());
        LogError logError = captor.getValue();
        assertEquals("Erro ao processar o arquivo CSV 'file1.csv': Erro ao mover o arquivo", logError.getError());
    }
}
