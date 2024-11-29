package com.fiap.techchallenge4.product.application.service;

import com.fiap.techchallenge4.product.application.exception.NotFoundException;
import com.fiap.techchallenge4.product.application.exception.ValidationException;
import com.fiap.techchallenge4.product.application.service.impl.ProcessBatchServiceImpl;
import com.fiap.techchallenge4.product.core.enums.StatusCsv;
import com.fiap.techchallenge4.product.core.model.CsvLoader;
import com.fiap.techchallenge4.product.core.model.LogError;
import com.fiap.techchallenge4.product.infrasctructure.utils.FileManipulationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;

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
    void setup() {
        processBatchService = new ProcessBatchServiceImpl(jobLauncher, productJob, csvLoaderService, logErrorService);
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
    void execute_deveProcessarListaDeArquivosComSucesso() throws Exception {
        // Arrange
        CsvLoader csvLoader = new CsvLoader();
        csvLoader.setId(1L);
        csvLoader.setFileName("file1.csv");
        csvLoader.setStatusCsv(StatusCsv.WAITING);

        when(csvLoaderService.findAllByStatusWaiting()).thenReturn(List.of(csvLoader));
        when(jobLauncher.run(eq(productJob), any(JobParameters.class))).thenReturn(mock(JobExecution.class));

        // Act
        processBatchService.execute();

        // Assert
        verify(csvLoaderService, times(1)).findAllByStatusWaiting();
        verify(csvLoaderService, times(1)).save(csvLoader);
        assertEquals(StatusCsv.FINISHED, csvLoader.getStatusCsv());
    }

    @Test
    void processBatch_deveAtualizarStatusParaFinishedComSucesso() throws Exception {
        // Arrange
        CsvLoader csvLoader = new CsvLoader();
        csvLoader.setId(1L);
        csvLoader.setFileName("file1.csv");
        csvLoader.setStatusCsv(StatusCsv.WAITING);

        when(jobLauncher.run(eq(productJob), any(JobParameters.class))).thenReturn(mock(JobExecution.class));

        // Act
        processBatchService.processBatch(csvLoader);

        // Assert
        verify(csvLoaderService, times(1)).save(csvLoader);
        assertEquals(StatusCsv.FINISHED, csvLoader.getStatusCsv());
        verify(fileManipulationUtils, times(1)).moveFile(anyString(), anyString(), eq("file1.csv"));
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

        assertEquals("Erro durante o processamento do arquivo CSV", validationException.getMessage());
        verify(csvLoaderService, times(1)).save(csvLoader);
        assertEquals(StatusCsv.ERROR, csvLoader.getStatusCsv());

        ArgumentCaptor<LogError> captor = ArgumentCaptor.forClass(LogError.class);
        verify(logErrorService, times(1)).save(captor.capture());
        LogError logError = captor.getValue();
        assertEquals("Erro ao processar o arquivo CSV 'file1.csv': Erro ao processar o arquivo", logError.getError());
    }
}
