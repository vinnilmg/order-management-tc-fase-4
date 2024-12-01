package com.fiap.techchallenge4.product.core.repository;

import com.fiap.techchallenge4.product.core.entity.CsvLoaderData;
import com.fiap.techchallenge4.product.core.enums.StatusCsv;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CsvLoaderRepositoryTest {

    @Mock
    private CsvLoaderRepository csvLoaderRepository;

    @Test
    void shouldFindAllByStatusCsv() {
        final var statusCsv = StatusCsv.FINISHED;

        CsvLoaderData csvLoader1 = CsvLoaderData.builder()
                .id(1L)
                .fileName("file1.csv")
                .statusCsv(statusCsv)
                .createdDate(LocalDateTime.now())
                .build();

        CsvLoaderData csvLoader2 = CsvLoaderData.builder()
                .id(2L)
                .fileName("file2.csv")
                .statusCsv(statusCsv)
                .createdDate(LocalDateTime.now())
                .build();

        List<CsvLoaderData> csvLoaderList = List.of(csvLoader1, csvLoader2);

        when(csvLoaderRepository.findAllByStatusCsv(statusCsv)).thenReturn(csvLoaderList);

        List<CsvLoaderData> result = csvLoaderRepository.findAllByStatusCsv(statusCsv);

        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .containsExactly(csvLoader1, csvLoader2);
    }

    @Test
    void shouldReturnEmptyListWhenNoDataFoundForStatusCsv() {
        final var statusCsv = StatusCsv.ERROR;

        when(csvLoaderRepository.findAllByStatusCsv(statusCsv)).thenReturn(List.of());

        List<CsvLoaderData> result = csvLoaderRepository.findAllByStatusCsv(statusCsv);

        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

}
