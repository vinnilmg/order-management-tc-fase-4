package com.fiap.techchallenge4.product.infrasctructure.config;

import com.fiap.techchallenge4.product.application.service.CsvLoaderService;
import com.fiap.techchallenge4.product.core.enums.StatusCsv;
import com.fiap.techchallenge4.product.core.model.CsvLoader;
import com.fiap.techchallenge4.product.infrasctructure.utils.FileManipulationUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class FileWatcherToProcessComponent {

    @Value("${directory.pending}")
    private String directoryPathPending;

    @Value("${directory.finished}")
    private String directoryPathToFinished;

    @Value("${directory.waiting}")
    private String directoryPathToWaiting;

    private final CsvLoaderService csvLoaderService;

    public FileWatcherToProcessComponent(CsvLoaderService csvLoaderService) {
        this.csvLoaderService = csvLoaderService;
    }

    @Scheduled(fixedDelay = 10000)
    public void watchDirectory() throws IOException {
        List<CsvLoader> csvLoaderList = new ArrayList<>();
        if (directoryPathPending != null) {

            File directory = new File(directoryPathPending);
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        log.info("Arquivo encontrado para carregar: {}.", file.getName());
                        CsvLoader csvToSave = new CsvLoader();
                        csvToSave.setPath(directoryPathToWaiting);
                        csvToSave.setStatusCsv(StatusCsv.WAITING);
                        csvToSave.setFileName(file.getName());

                        csvLoaderList.add(csvToSave);

                        FileManipulationUtils.moveFile(directoryPathPending,directoryPathToWaiting,csvToSave.getFileName());
                    }
                }
            }
            if (!csvLoaderList.isEmpty()) {
                csvLoaderService.saveAll(csvLoaderList);
            }
        }
    }


}
