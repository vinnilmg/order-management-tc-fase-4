package com.fiap.techchallenge4.product.infrasctructure.config;

import com.fiap.techchallenge4.product.application.service.CsvLoaderService;
import com.fiap.techchallenge4.product.application.service.FileManipulationService;
import com.fiap.techchallenge4.product.core.enums.StatusCsv;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileWatcherToProcessComponentTest {

    @Mock
    private CsvLoaderService csvLoaderService;

    @Mock
    private FileManipulationService fileManipulationService;

    @InjectMocks
    private FileWatcherToProcessComponent fileWatcherToProcessComponent;

    private File pendingDirectory;
    private File waitingDirectory;

    @BeforeAll
    void setupDirectories() throws IOException {
        // Criar os diretórios de teste
        pendingDirectory = new File("src/test/resources/pending");
        waitingDirectory = new File("src/test/resources/waiting");
        if (!pendingDirectory.exists()) {
            pendingDirectory.mkdirs();
        }
        if (!waitingDirectory.exists()) {
            waitingDirectory.mkdirs();
        }
    }

    @AfterAll
    void cleanupDirectories() throws IOException {
        deleteDirectory(pendingDirectory);
        deleteDirectory(waitingDirectory);
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        fileWatcherToProcessComponent = new FileWatcherToProcessComponent(csvLoaderService, fileManipulationService);
        ReflectionTestUtils.setField(fileWatcherToProcessComponent, "directoryPathPending", pendingDirectory.getPath());
        ReflectionTestUtils.setField(fileWatcherToProcessComponent, "directoryPathToWaiting", waitingDirectory.getPath());
    }

    @AfterEach
    void resetMocks() {
        // Resetando mocks entre os testes
        if (csvLoaderService != null) {
            reset(csvLoaderService);
        }
        if (fileManipulationService != null) {
            reset(fileManipulationService);
        }
    }

    @Test
    void shouldProcessFilesInPendingDirectory() throws IOException, URISyntaxException {
        // Criando arquivos de teste
        File file1 = new File(pendingDirectory, "file1.csv");
        File file2 = new File(pendingDirectory, "file2.csv");
        file1.createNewFile();
        file2.createNewFile();

        // Mocking FileManipulationService.moveFile
        doNothing().when(fileManipulationService).moveFile(eq(pendingDirectory.getPath()), eq(waitingDirectory.getPath()), anyString());

        fileWatcherToProcessComponent.watchDirectory();

        // Verificando que saveAll foi chamado para os arquivos processados
        verify(csvLoaderService, times(1)).saveAll(argThat(csvLoaderList -> {
            return csvLoaderList.size() == 2 &&
                    csvLoaderList.stream().allMatch(loader -> loader.getStatusCsv() == StatusCsv.WAITING);
        }));

        // Verificando que moveFile foi chamado duas vezes, uma para cada arquivo
        verify(fileManipulationService, times(2)).moveFile(eq(pendingDirectory.getPath()), eq(waitingDirectory.getPath()), anyString());
    }

    @Test
    void shouldDoNothingIfPendingDirectoryIsEmpty() throws IOException, URISyntaxException {
        fileWatcherToProcessComponent.watchDirectory();

        // Verificando que saveAll não foi chamado
        verify(csvLoaderService, never()).saveAll(anyList());
    }

    private void deleteDirectory(File directory) throws IOException {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }
        if (directory.exists()) {
            if (directory.delete()) {
                System.out.println("Deleted: " + directory);
            } else {
                System.err.println("Failed to delete: " + directory);
            }
        }
    }
}
