package com.fiap.techchallenge4.product.infrasctructure.utils;

import com.fiap.techchallenge4.product.application.service.LogErrorService;
import com.fiap.techchallenge4.product.core.model.CsvLoader;
import com.fiap.techchallenge4.product.core.model.LogError;
import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Slf4j
public class FileManipulationUtils {

    private final LogErrorService logErrorService;

    public FileManipulationUtils(LogErrorService logErrorService) {
        this.logErrorService = logErrorService;
    }

    public static void moveFile(String directoryResource, String directoryDestination, String fileName) throws IOException {
        File file = new File(directoryResource + File.separator + fileName);
        Path targetPath = Path.of(directoryDestination + File.separator + fileName);

        try {
            Files.move(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            log.info("Arquivo {} movido para a pasta {}.", file.getPath(), targetPath.toString());
        } catch (IOException e) {
            String errorMessage = String.format("Erro ao mover o arquivo %s para a pasta %s: %s", file.getPath(), targetPath.toString(), e.getMessage());
            log.error(errorMessage);
            throw new IOException(errorMessage, e);
        }
    }
}
