package com.fiap.techchallenge4.product.application.service;

import com.fiap.techchallenge4.product.application.controller.mapper.CsvLoaderMapper;
import com.fiap.techchallenge4.product.application.service.impl.CsvLoaderServiceImpl;
import com.fiap.techchallenge4.product.core.entity.CsvLoaderData;
import com.fiap.techchallenge4.product.core.enums.StatusCsv;
import com.fiap.techchallenge4.product.core.model.CsvLoader;
import com.fiap.techchallenge4.product.core.repository.CsvLoaderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class CsvLoaderServiceImplTest {

    @Mock
    private CsvLoaderRepository csvLoaderRepository;

    @Mock
    private CsvLoaderMapper csvLoaderMapper;

    @InjectMocks
    private CsvLoaderServiceImpl csvLoaderService;

    @Test
    void findAllByStatusPending_deveRetornarListaDeCsvLoaders() {
        List<CsvLoaderData> entities = List.of(
                CsvLoaderData.builder()
                        .id(1L)
                        .fileName("file1.csv")
                        .statusCsv(StatusCsv.PENDING)
                        .build(),
                CsvLoaderData.builder()
                        .id(2L)
                        .fileName("file2.csv")
                        .statusCsv(StatusCsv.PENDING)
                        .build()
        );
        List<CsvLoader> models = List.of(
                CsvLoader.builder()
                        .id(1L)
                        .fileName("file1.csv")
                        .statusCsv(StatusCsv.PENDING)
                        .build(),
                CsvLoader.builder()
                        .id(2L)
                        .fileName("file2.csv")
                        .statusCsv(StatusCsv.PENDING)
                        .build()
        );

        Mockito.when(csvLoaderRepository.findAllByStatusCsv(StatusCsv.PENDING)).thenReturn(entities);
        Mockito.when(csvLoaderMapper.toModel(entities.get(0))).thenReturn(models.get(0));
        Mockito.when(csvLoaderMapper.toModel(entities.get(1))).thenReturn(models.get(1));

        List<CsvLoader> result = csvLoaderService.findAllByStatusPending();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("file1.csv", result.get(0).getFileName());
    }

    @Test
    void findAllByStatusWaiting_deveRetornarListaDeCsvLoaders() {
        List<CsvLoaderData> entities = List.of(
                CsvLoaderData.builder()
                        .id(1L)
                        .fileName("file1.csv")
                        .statusCsv(StatusCsv.WAITING)
                        .build()
        );
        List<CsvLoader> models = List.of(
                CsvLoader.builder()
                        .id(1L)
                        .fileName("file1.csv")
                        .statusCsv(StatusCsv.WAITING)
                        .build()
        );

        Mockito.when(csvLoaderRepository.findAllByStatusCsv(StatusCsv.WAITING)).thenReturn(entities);
        Mockito.when(csvLoaderMapper.toModel(entities.get(0))).thenReturn(models.get(0));

        List<CsvLoader> result = csvLoaderService.findAllByStatusWaiting();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("file1.csv", result.get(0).getFileName());
    }

    @Test
    void saveAll_deveSalvarListaDeCsvLoaders() {
        List<CsvLoader> models = List.of(
                CsvLoader.builder()
                        .id(1L)
                        .fileName("file1.csv")
                        .statusCsv(StatusCsv.PENDING)
                        .build()
        );
        List<CsvLoaderData> entities = List.of(
                CsvLoaderData.builder()
                        .id(1L)
                        .fileName("file1.csv")
                        .statusCsv(StatusCsv.PENDING)
                        .build()
        );

        Mockito.when(csvLoaderMapper.toEntity(models.get(0))).thenReturn(entities.get(0));

        csvLoaderService.saveAll(models);

        Mockito.verify(csvLoaderRepository).saveAllAndFlush(entities);
    }

    @Test
    void save_deveSalvarEMapearCsvLoader() {
        CsvLoader model = CsvLoader.builder()
                .id(1L)
                .fileName("file1.csv")
                .statusCsv(StatusCsv.PENDING)
                .build();

        CsvLoaderData entity = CsvLoaderData.builder()
                .id(1L)
                .fileName("file1.csv")
                .statusCsv(StatusCsv.PENDING)
                .build();

        Mockito.when(csvLoaderMapper.toEntity(model)).thenReturn(entity);
        Mockito.when(csvLoaderRepository.saveAndFlush(entity)).thenReturn(entity);
        Mockito.when(csvLoaderMapper.toModel(entity)).thenReturn(model);

        CsvLoader result = csvLoaderService.save(model);

        assertNotNull(result);
        assertEquals("file1.csv", result.getFileName());
        Mockito.verify(csvLoaderRepository).saveAndFlush(entity);
    }
}
