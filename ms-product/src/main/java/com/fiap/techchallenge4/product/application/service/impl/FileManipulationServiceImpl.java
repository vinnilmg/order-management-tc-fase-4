package com.fiap.techchallenge4.product.application.service.impl;

import com.fiap.techchallenge4.product.application.service.FileManipulationService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Slf4j
@NoArgsConstructor
@Service
public class FileManipulationServiceImpl implements FileManipulationService {

    @Override
    public void moveFile(String directoryResource, String directoryDestination, String fileName) throws IOException {
        File file = new File(directoryResource + File.separator + fileName);
        Path targetPath = Path.of(directoryDestination + File.separator + fileName);

        if (!fileName.endsWith(".csv")) {
            return;
        }

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
