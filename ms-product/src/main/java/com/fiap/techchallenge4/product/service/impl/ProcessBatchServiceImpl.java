package com.fiap.techchallenge4.product.service.impl;

import com.fiap.techchallenge4.product.model.CsvLoader;
import com.fiap.techchallenge4.product.service.CsvLoaderService;
import com.fiap.techchallenge4.product.service.ProcessBatchService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@AllArgsConstructor
@Slf4j
public class ProcessBatchServiceImpl implements ProcessBatchService {

    private final JobLauncher jobLauncher;
    private final Job productJob;
    private final Logger logger = Logger.getLogger(String.valueOf(ProcessBatchServiceImpl.class));
    private final CsvLoaderService csvLoaderService;

    @Override
    public void execute() {
        var csvLoaderList = csvLoaderService.findAllByStatusWaiting();

        if (!csvLoaderList.isEmpty()) {
            csvLoaderList.forEach(this::processBatch);
        }
    }

    private void processBatch(CsvLoader csvLoader) {
        try {
            logger.info(String.format("found %s to load ", csvLoader.getFileName()));
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("pathWithFileName", csvLoader.directoryPathWithFileName())
                    .toJobParameters();

            jobLauncher.run(productJob, jobParameters);
            logger.info(String.format("file -> %s to load sucessfully ", csvLoader.getFileName()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
