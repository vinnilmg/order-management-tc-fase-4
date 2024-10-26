package com.fiap.techchallenge4.product.service.impl;

import com.fiap.techchallenge4.product.batch.writer.helper.FileWatcherToProcess;
import com.fiap.techchallenge4.product.service.ProcessBatchService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
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

    @Override
    public void execute() {
        var filesNames = FileWatcherToProcess.getFileNames();
        if (!filesNames.isEmpty() && filesNames != null) {
            for (String fileName : FileWatcherToProcess.getFileNames()) {
                try {
                    logger.info(String.format("found %s to load ", fileName));
                    JobParameters jobParameters = new JobParametersBuilder()
                            .addLong("startAt", System.currentTimeMillis())
                            .addString("fileNames", fileName)
                            .toJobParameters();

                    jobLauncher.run(productJob, jobParameters);
                    logger.info(String.format("file -> %s to load sucessfully ", fileName));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
