package com.fiap.techchallenge4.product.batch.writer.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class FileWatcherToProcess {

    private static final List<String> fileNames = new ArrayList<>();

    @Value("${directory.watch}")
    private static String directoryPath;

    @Scheduled(fixedDelay = 10000)
    public void watchDirectory() {
        if (directoryPath != null) {
            File directory = new File(directoryPath);

            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && !fileNames.contains(file.getName())) {
                        fileNames.add(file.getName());
                    }
                }
            }
        }
    }

    public static List<String> getFileNames() {
        return new ArrayList<>(fileNames);
    }

    public static String directoryPathWithFileName(String name) {
        return directoryPath + File.separator + name;
    }
}
