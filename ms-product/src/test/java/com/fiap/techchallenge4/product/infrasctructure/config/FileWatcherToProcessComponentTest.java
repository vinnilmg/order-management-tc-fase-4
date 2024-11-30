package com.fiap.techchallenge4.product.infrasctructure.config;


import com.fiap.techchallenge4.product.application.service.CsvLoaderService;
import com.fiap.techchallenge4.product.core.enums.StatusCsv;
import com.fiap.techchallenge4.product.infrasctructure.utils.FileManipulationUtils;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileWatcherToProcessComponentTest {

    @Mock
    private CsvLoaderService csvLoaderService;

    @InjectMocks
    private FileWatcherToProcessComponent fileWatcherToProcessComponent;

    private File pendingDirectory;
    private File waitingDirectory;

    @BeforeAll
    void setupDirectories() {
        // Criar os diretórios de teste
        pendingDirectory = new File("src/test/resources/pending");
        waitingDirectory = new File("src/test/resources/waiting");
        pendingDirectory.mkdirs();
        waitingDirectory.mkdirs();
    }

    @AfterAll
    void cleanupDirectories() {
        deleteDirectory(pendingDirectory);
        deleteDirectory(waitingDirectory);
    }

    @BeforeEach
    void setup() {

        MockitoAnnotations.openMocks(this);

        fileWatcherToProcessComponent = new FileWatcherToProcessComponent(csvLoaderService);
        ReflectionTestUtils.setField(fileWatcherToProcessComponent, "directoryPathPending", pendingDirectory.getPath());
        ReflectionTestUtils.setField(fileWatcherToProcessComponent, "directoryPathToWaiting", waitingDirectory.getPath());
    }

    @Test
    void shouldProcessFilesInPendingDirectory() throws IOException {
        // Arrange: Criar arquivos no diretório "pending"
        File file1 = new File(pendingDirectory, "file1.csv");
        File file2 = new File(pendingDirectory, "file2.csv");
        file1.createNewFile();
        file2.createNewFile();

        // Mock do utilitário de manipulação de arquivos
        mockStatic(FileManipulationUtils.class);

        // Act: Executar o método a ser testado
        fileWatcherToProcessComponent.watchDirectory();

        // Assert: Verificar se os arquivos foram processados
        verify(csvLoaderService, times(1)).saveAll(argThat(csvLoaderList -> {
            // Valida que os CSVs foram salvos corretamente
            return csvLoaderList.size() == 2 &&
                    csvLoaderList.stream().allMatch(loader -> loader.getStatusCsv() == StatusCsv.WAITING);
        }));

        // Verificar se os arquivos foram movidos
        verify(FileManipulationUtils.class, times(2));
        FileManipulationUtils.moveFile(eq(pendingDirectory.getPath()), eq(waitingDirectory.getPath()), anyString());
    }

    @Test
    void shouldDoNothingIfPendingDirectoryIsEmpty() throws IOException {
        // Act: Executar o método a ser testado com diretório vazio
        fileWatcherToProcessComponent.watchDirectory();

        // Assert: Verificar que o serviço não foi chamado
        verify(csvLoaderService, never()).saveAll(anyList());
    }

    private void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }
        directory.delete();
    }
}
