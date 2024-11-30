package com.fiap.techchallenge4.product.infrasctructure.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FileManipulationUtilsTest {

    private Path resourceDirectory;
    private Path destinationDirectory;
    private Path testFile;

    @BeforeEach
    void setUp() throws IOException {

        resourceDirectory = Files.createTempDirectory("test-resource");
        destinationDirectory = Files.createTempDirectory("test-destination");
        testFile = Files.createFile(resourceDirectory.resolve("test-file.txt"));

        Files.writeString(testFile, "Conteúdo de teste");
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(testFile);
        Files.deleteIfExists(destinationDirectory.resolve(testFile.getFileName()));
        Files.deleteIfExists(resourceDirectory);
        Files.deleteIfExists(destinationDirectory);
    }

    @Test
    @DisplayName("Deve mover arquivo com sucesso para o diretório de destino")
    void shouldMoveFileSuccessfully() throws IOException {
        FileManipulationUtils.moveFile(resourceDirectory.toString(), destinationDirectory.toString(), testFile.getFileName().toString());

        assertThat(Files.exists(testFile)).isFalse(); // Arquivo original não deve existir
        assertThat(Files.exists(destinationDirectory.resolve(testFile.getFileName()))).isTrue(); // Arquivo deve estar no destino
        assertThat(Files.readString(destinationDirectory.resolve(testFile.getFileName()))).isEqualTo("Conteúdo de teste"); // Conteúdo inalterado
    }

    @Test
    @DisplayName("Deve lançar exceção se o arquivo não existir")
    void shouldThrowExceptionWhenFileDoesNotExist() {
        String nonExistentFileName = "non-existent-file.txt";
        IOException exception = assertThrows(IOException.class, () ->
                FileManipulationUtils.moveFile(resourceDirectory.toString(), destinationDirectory.toString(), nonExistentFileName)
        );
        assertThat(exception.getMessage()).contains(nonExistentFileName);
    }

}
