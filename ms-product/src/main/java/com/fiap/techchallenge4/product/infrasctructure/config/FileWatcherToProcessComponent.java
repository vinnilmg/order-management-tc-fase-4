package com.fiap.techchallenge4.product.infrasctructure.config;

import com.fiap.techchallenge4.product.core.enums.StatusCsv;
import com.fiap.techchallenge4.product.core.model.CsvLoader;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
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

    @Value("${directory.watch}")
    private String directoryPathToRead;

    @Value("${directory.move}")
    private String directoryPathToMove;

    @Value("${directory.waiting}")
    private String directoryPathToWaiting;

    private final DataSource dataSource;

    public FileWatcherToProcessComponent(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Scheduled(fixedDelay = 10000)
    @Transactional
    public void watchDirectory() {
        List<CsvLoader> csvLoaderList = new ArrayList<>();
        if (directoryPathToRead != null) {

            File directory = new File(directoryPathToRead);
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        log.info("Found file to be loaded: {}.", file.getName());
                        CsvLoader csvToSave = new CsvLoader();
                        csvToSave.setPath(directoryPathToWaiting);
                        csvToSave.setStatusCsv(StatusCsv.WAITING);
                        csvToSave.setFileName(file.getName());

                        csvLoaderList.add(csvToSave);

                        moveFilesToWaitingirectory(csvToSave);
                    }
                }
            }
            if (!csvLoaderList.isEmpty()) {
                csvLoaderList.forEach(this::inserirCsvLoaderData);
            }
        }
    }

    private void moveFilesToWaitingirectory(CsvLoader csvLoader) {
        File file = new File(directoryPathToRead + File.separator + csvLoader.getFileName());
        Path targetPath = Path.of(directoryPathToWaiting + File.separator + csvLoader.getFileName());
        try {
            Files.move(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            log.info("Archive: {}.", file.getName()+ " moved to 'waiting'");
        } catch (Exception e) {
            log.error("Error moving the file {}: {}", csvLoader.getFileName(), e.getMessage());
        }
    }


    public void inserirCsvLoaderData(CsvLoader csvLoader) {
        String sql = "INSERT INTO csv_loader (file_name, path, status_csv, created_date) VALUES (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
            ) {
                preparedStatement.setString(1, csvLoader.getFileName());
                preparedStatement.setString(2, csvLoader.getPath());
                preparedStatement.setString(3, csvLoader.getStatusCsv().name());
                preparedStatement.setObject(4, LocalDateTime.now());
                preparedStatement.executeUpdate();

        } catch (SQLException e) {
            log.error("Error to save csv in csv_loader: fileName : {} , error: {} ", csvLoader.getFileName(), e.getMessage());
        }
    }
}