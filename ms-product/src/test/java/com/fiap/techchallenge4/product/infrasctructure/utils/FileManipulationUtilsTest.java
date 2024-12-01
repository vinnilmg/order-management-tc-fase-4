package com.fiap.techchallenge4.product.infrasctructure.utils;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class FileManipulationUtilsTest {

    private static File resourceDirectory;
    private static File destinationDirectory;
    private static File testFile;
    private File pendingDirectory;
    private File waitingDirectory;
    private MockedStatic<FileManipulationUtils> mockedStatic;

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
    }

    @AfterEach
    void cleanupFiles() throws IOException {
        if (testFile.exists()) Files.deleteIfExists(testFile.toPath());
        Files.deleteIfExists(pendingDirectory.toPath());
        Files.deleteIfExists(waitingDirectory.toPath());

        if (mockedStatic != null) {
            mockedStatic.close();
            mockedStatic = null;
        }
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
        mockedStatic = mockStatic(FileManipulationUtils.class);

        // Act: Mover o arquivo para o diretório de destino
        FileManipulationUtils.moveFile(resourceDirectory.getPath(), destinationDirectory.getPath(), testFile.getName());

        // Assert: Arquivo movido com sucesso
        assertThat(testFile.exists()).isTrue();
        assertThat( Arrays.stream(destinationDirectory.listFiles()).findAny().get().getName()).isEqualTo(testFile.getName());
    }

    @Test
    @DisplayName("Deve lançar exceção se o arquivo não existir")
    void shouldThrowExceptionWhenFileDoesNotExist() {
        String nonExistentFileName = "non-existent-file.txt";

        IOException exception = assertThrows(IOException.class, () ->
                FileManipulationUtils.moveFile(resourceDirectory.getPath(), destinationDirectory.getPath(), nonExistentFileName)
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
