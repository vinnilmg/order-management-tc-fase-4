package com.fiap.techchallenge4.product.infrasctructure.config;

import com.fiap.techchallenge4.product.application.service.CsvLoaderService;
import com.fiap.techchallenge4.product.application.service.FileManipulationService;
import com.fiap.techchallenge4.product.core.enums.StatusCsv;
import com.fiap.techchallenge4.product.core.model.CsvLoader;
import com.sun.tools.javac.Main;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class FileWatcherToProcessComponent {


    @Value("${app.file-directory.pending}")
    private String directoryPathPending;

    @Value("${app.file-directory.waiting}")
    private String directoryPathToWaiting;

    @Value("${app.file-directory.finished}")
    private String directoryPathToFinished;

    private final CsvLoaderService csvLoaderService;

    private final FileManipulationService fileManipulationService;

    public FileWatcherToProcessComponent(CsvLoaderService csvLoaderService, FileManipulationService fileManipulationService) {
        this.csvLoaderService = csvLoaderService;
        this.fileManipulationService = fileManipulationService;
    }

    @Scheduled(fixedDelay = 10000)
    public void watchDirectory() throws IOException, URISyntaxException {
        List<CsvLoader> csvLoaderList = new ArrayList<>();
        if (directoryPathPending != null) {

            File directory = new File(directoryPathPending);
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".csv")) {
                        log.info("Arquivo encontrado para carregar: {}.", file.getName());
                        CsvLoader csvToSave = new CsvLoader();
                        csvToSave.setPath(directoryPathToWaiting);
                        csvToSave.setStatusCsv(StatusCsv.WAITING);
                        csvToSave.setFileName(file.getName());

                        csvLoaderList.add(csvToSave);

                        fileManipulationService.moveFile(directoryPathPending,directoryPathToWaiting,csvToSave.getFileName());
                    }
                }
            }
            if (!csvLoaderList.isEmpty()) {
                csvLoaderService.saveAll(csvLoaderList);
            }
        }
    }


}
