package com.fiap.techchallenge4.product.infrasctructure.utils;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class FileManipulationUtilsTest {

    private static File resourceDirectory;
    private static File destinationDirectory;
    private static File testFile;
    private File pendingDirectory;
    private File waitingDirectory;

    @BeforeAll
    static void setupDirectories() {
        // Criar os diretórios de teste
        resourceDirectory = new File("src/test/resources/test-resource");
        destinationDirectory = new File("src/test/resources/test-destination");
        resourceDirectory.mkdirs();
        destinationDirectory.mkdirs();
    }

    @BeforeEach
    void setUp() throws IOException {
        // Criar o arquivo de teste
        testFile = new File(resourceDirectory, "test-file.txt");

        if (testFile.createNewFile()) {
            // Adicionar conteúdo ao arquivo
            try (FileWriter writer = new FileWriter(testFile)) {
                writer.write("Conteúdo de teste");
            }
        }

        // Criar diretórios adicionais
        pendingDirectory = new File("src/test/resources/pending");
        waitingDirectory = new File("src/test/resources/waiting");
        pendingDirectory.mkdirs();
        waitingDirectory.mkdirs();
    }

    @AfterEach
    void cleanupFiles() {
        // Excluir os arquivos criados após cada teste
        if (testFile.exists()) testFile.delete();
    }

    @AfterAll
    static void cleanupDirectories() {
        // Excluir os diretórios criados após todos os testes
        if (resourceDirectory.exists()) resourceDirectory.delete();
        if (destinationDirectory.exists()) destinationDirectory.delete();
    }

    @Test
    @DisplayName("Deve mover arquivo com sucesso para o diretório de destino")
    void shouldMoveFileSuccessfully() throws IOException {
        // Act: Mover o arquivo para o diretório de destino
        FileManipulationUtils.moveFile(resourceDirectory.getPath(), destinationDirectory.getPath(), testFile.getName());

        // Assert: Verificar se o arquivo foi movido com sucesso
        assertThat(testFile.exists()).isFalse(); // Arquivo original não deve existir
        assertThat(new File(destinationDirectory, testFile.getName()).exists()).isTrue(); // Arquivo deve estar no destino
        assertThat(new File(destinationDirectory, testFile.getName()).length()).isGreaterThan(0); // Verificar se o conteúdo está no arquivo
    }

    @Test
    @DisplayName("Deve lançar exceção se o arquivo não existir")
    void shouldThrowExceptionWhenFileDoesNotExist() {
        String nonExistentFileName = "non-existent-file.txt";

        // Act & Assert: Verificar que exceção é lançada ao tentar mover um arquivo inexistente
        IOException exception = assertThrows(IOException.class, () ->
                FileManipulationUtils.moveFile(resourceDirectory.getPath(), destinationDirectory.getPath(), nonExistentFileName)
        );
        assertThat(exception.getMessage()).contains(nonExistentFileName);
    }
}
