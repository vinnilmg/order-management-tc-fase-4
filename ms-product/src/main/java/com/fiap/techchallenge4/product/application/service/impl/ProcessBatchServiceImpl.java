package com.fiap.techchallenge4.product.application.service.impl;

import com.fiap.techchallenge4.product.application.exception.NotFoundException;
import com.fiap.techchallenge4.product.application.exception.ValidationException;
import com.fiap.techchallenge4.product.core.model.CsvLoader;
import com.fiap.techchallenge4.product.core.model.LogError;
import com.fiap.techchallenge4.product.application.service.CsvLoaderService;
import com.fiap.techchallenge4.product.application.service.LogErrorService;
import com.fiap.techchallenge4.product.application.service.ProcessBatchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
@Slf4j
public class ProcessBatchServiceImpl implements ProcessBatchService {

    private final JobLauncher jobLauncher;
    private final Job productJob;
    private final CsvLoaderService csvLoaderService;
    private final LogErrorService logErrorService;

    @Override
    public void execute() {
        var csvLoaderList = csvLoaderService.findAllByStatusWaiting();
        if (csvLoaderList.isEmpty()) {
            throw NotFoundException.of("CSV");
        }
        csvLoaderList.forEach(this::processBatch);

    }

    private void processBatch(CsvLoader csvLoader) {
        try {
            log.info("found {} to load ", csvLoader.getFileName());
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("pathWithFileName", csvLoader.directoryPathWithFileName())
                    .toJobParameters();

            jobLauncher.run(productJob, jobParameters);
            log.info("file -> {} to load sucessfully ", csvLoader.getFileName());
        } catch (Exception e) {
            log.error(e.getMessage());
            logErrorService.save(LogError.builder()
                    .className(e.getLocalizedMessage())
                    .error(e.getMessage())
                    .build());
            throw ValidationException.of(String.valueOf(e.getClass()), e.getMessage());
        }
    }
}
