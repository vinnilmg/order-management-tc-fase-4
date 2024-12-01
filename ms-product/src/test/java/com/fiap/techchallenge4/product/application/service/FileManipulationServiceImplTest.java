package com.fiap.techchallenge4.product.application.service;

import com.fiap.techchallenge4.product.application.service.impl.FileManipulationServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class FileManipulationServiceImplTest {

    private static File resourceDirectory;
    private static File destinationDirectory;
    private static File testFile;
    private File pendingDirectory;
    private File waitingDirectory;
    private FileManipulationServiceImpl fileManipulationService;

    @BeforeAll
    static void setupDirectories() throws IOException {
        resourceDirectory = new File("src/test/resources/test-resource");
        destinationDirectory = new File("src/test/resources/test-destination");
        Files.createDirectories(resourceDirectory.toPath());
        Files.createDirectories(destinationDirectory.toPath());
    }

    @BeforeEach
    void setUp() throws IOException {
        testFile = new File(resourceDirectory, "test-file.txt");

        if (testFile.createNewFile()) {
            try (FileWriter writer = new FileWriter(testFile)) {
                writer.write("Conteúdo de teste");
            }
        }

        pendingDirectory = new File("src/test/resources/pending");
        waitingDirectory = new File("src/test/resources/waiting");
        Files.createDirectories(pendingDirectory.toPath());
        Files.createDirectories(waitingDirectory.toPath());

        // Criação da instância do serviço
        fileManipulationService = new FileManipulationServiceImpl();
    }

    @AfterEach
    void cleanupFiles() throws IOException {
        if (testFile.exists()) Files.deleteIfExists(testFile.toPath());
        Files.deleteIfExists(pendingDirectory.toPath());
        Files.deleteIfExists(waitingDirectory.toPath());
    }

    @AfterAll
    static void cleanupDirectories() throws IOException {
        deleteDirectory(destinationDirectory);
        Files.deleteIfExists(resourceDirectory.toPath());
        Files.deleteIfExists(destinationDirectory.toPath());
    }

    @Test
    @DisplayName("Deve mover arquivo com sucesso para o diretório de destino")
    void shouldMoveFileSuccessfully() throws IOException {
        // Act: Mover o arquivo para o diretório de destino
        fileManipulationService.moveFile(resourceDirectory.getPath(), destinationDirectory.getPath(), testFile.getName());

        // Assert: Arquivo movido com sucesso
        assertThat(testFile.exists()).isFalse();
    }

    @Test
    @DisplayName("Deve lançar exceção se o arquivo não existir")
    void shouldThrowExceptionWhenFileDoesNotExist() {
        String nonExistentFileName = "non-existent-file.txt";

        IOException exception = assertThrows(IOException.class, () ->
                fileManipulationService.moveFile(resourceDirectory.getPath(), destinationDirectory.getPath(), nonExistentFileName)
        );
        assertThat(exception.getMessage()).contains(nonExistentFileName);
    }

    private static void deleteDirectory(File directory) throws IOException {
        // Verifica se o diretório existe
        if (directory.exists()) {
            // Deleta todos os arquivos no diretório
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);  // Recursão para subdiretórios
                    } else {
                        Files.deleteIfExists(file.toPath());  // Deleta arquivos
                    }
                }
            }
            // Depois de apagar os arquivos, deleta o diretório
            Files.deleteIfExists(directory.toPath());
        }
    }
}
