package com.fiap.techchallenge4.product.batch.writer.helper;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

//@Component
public class ResourcesHelper {

    private static final String pathResources = "../src/main/resources/imports/pending";

    private static final Path folderPath = Path.of(pathResources);

    @Bean
    public static List<String> readNamedFilesCsvToRead () throws IOException {
       List<String> filesToRead = new ArrayList<>();

        Files.list(folderPath).forEach(files-> {
            filesToRead.add(files.getFileName().toString());
        } );

        return filesToRead;

    }
}
