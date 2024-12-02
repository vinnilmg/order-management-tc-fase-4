package com.fiap.techchallenge4.product.application.service.impl;

import com.fiap.techchallenge4.product.application.exception.NotFoundException;
import com.fiap.techchallenge4.product.application.exception.ValidationException;
import com.fiap.techchallenge4.product.application.service.FileManipulationService;
import com.fiap.techchallenge4.product.core.enums.StatusCsv;
import com.fiap.techchallenge4.product.core.model.CsvLoader;
import com.fiap.techchallenge4.product.core.model.LogError;
import com.fiap.techchallenge4.product.application.service.CsvLoaderService;
import com.fiap.techchallenge4.product.application.service.LogErrorService;
import com.fiap.techchallenge4.product.application.service.ProcessBatchService;
import jakarta.transaction.Transactional;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Setter
@Slf4j
public class ProcessBatchServiceImpl implements ProcessBatchService {

    private final JobLauncher jobLauncher;
    private final Job productJob;
    private final CsvLoaderService csvLoaderService;
    private final LogErrorService logErrorService;
    private final FileManipulationService fileManipulationService;

    @Value("${directory.finished}")
    private String directoryPathToFinished;

    @Value("${directory.waiting}")
    private String directoryPathToWaiting;

    public ProcessBatchServiceImpl(JobLauncher jobLauncher, Job productJob, CsvLoaderService csvLoaderService, LogErrorService logErrorService, FileManipulationService fileManipulationService) {
        this.jobLauncher = jobLauncher;
        this.productJob = productJob;
        this.csvLoaderService = csvLoaderService;
        this.logErrorService = logErrorService;
        this.fileManipulationService = fileManipulationService;
    }

    @Override
    public void execute() {
        var csvLoaderList = csvLoaderService.findAllByStatusWaiting();
        if (csvLoaderList.isEmpty()) {
            throw NotFoundException.of("Nenhum arquivo CSV com status 'WAITING' encontrado para processamento.");
        }
        csvLoaderList.forEach(this::processBatch);
    }

    @Transactional
    public void processBatch(CsvLoader csvLoader) {
        try {
            log.info("Iniciando o carregamento do arquivo: {}", csvLoader.getFileName());
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("pathWithFileName", csvLoader.directoryPathWithFileName())
                    .toJobParameters();

            jobLauncher.run(productJob, jobParameters);
            log.info("Arquivo '{}' carregado com sucesso.", csvLoader.getFileName());
            log.info("Movendo o arquivo CSV '{}' para a pasta de arquivos processados.", csvLoader.getFileName());



            log.info("Status do arquivo CSV '{}' atualizado para FINISHED.", csvLoader.getFileName());
            log.info("Movendo arquivo'{}' para pasta FINISHED.", csvLoader.getFileName());

            fileManipulationService.moveFile(directoryPathToWaiting,directoryPathToFinished,csvLoader.getFileName());

            csvLoader.setStatusCsv(StatusCsv.FINISHED);
            csvLoaderService.save(csvLoader);

        } catch (Exception e) {
            String errorMessage = String.format("Erro ao processar o arquivo CSV '%s': %s", csvLoader.getFileName(), e.getMessage());
            log.error(errorMessage, e);

            logErrorService.save(LogError.builder()
                    .className(e.getClass().getName())
                    .error(errorMessage)
                    .idEntity(csvLoader.getId())
                    .build());

            csvLoader.setStatusCsv(StatusCsv.ERROR);
            csvLoaderService.save(csvLoader);
            throw ValidationException.of("Erro durante o processamento do arquivo CSV", e.getMessage());
        }
    }
}
