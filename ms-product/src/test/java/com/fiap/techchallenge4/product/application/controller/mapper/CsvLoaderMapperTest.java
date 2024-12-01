package com.fiap.techchallenge4.product.application.controller.mapper;

import com.fiap.techchallenge4.product.core.entity.CsvLoaderData;
import com.fiap.techchallenge4.product.core.entity.LogErrorData;
import com.fiap.techchallenge4.product.core.enums.StatusCsv;
import com.fiap.techchallenge4.product.core.model.CsvLoader;
import com.fiap.techchallenge4.product.core.model.LogError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CsvLoaderMapperTest {

    private  CsvLoaderMapper csvLoaderMapper;

    @BeforeEach
    void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        csvLoaderMapper = new CsvLoaderMapper(modelMapper);
    }

    @Test
    void testToEntity() {
        // Arrange
        CsvLoader csvLoader = CsvLoader.builder()
                .statusCsv(StatusCsv.PENDING)
                .fileName("Teste Name File")
                .id(1L)
                .path("/teste")
                .createdDate(LocalDateTime.now())
                .build();

        // Act
        CsvLoaderData csvLoaderData = csvLoaderMapper.toEntity(csvLoader);

        // Assert
        assertThat(csvLoader)
                .isNotNull()
                .extracting(
                        CsvLoader::getId,
                        CsvLoader::getStatusCsv,
                        CsvLoader::getPath,
                        CsvLoader::getFileName,
                        CsvLoader::getCreatedDate
                )
                .containsExactly(
                        csvLoaderData.getId(),
                        csvLoaderData.getStatusCsv(),
                        csvLoaderData.getPath(),
                        csvLoaderData.getFileName(),
                        csvLoaderData.getCreatedDate()
                );
    }

    @Test
    void testToModel() {
        // Arrange
        CsvLoaderData csvLoaderData = CsvLoaderData.builder()
                .statusCsv(StatusCsv.PENDING)
                .fileName("Teste Name File")
                .id(1L)
                .path("/teste")
                .createdDate(LocalDateTime.now())
                .build();

        // Act
        CsvLoader csvLoader = csvLoaderMapper.toModel(csvLoaderData);

        // Assert
        assertThat(csvLoader)
                .isNotNull()
                .extracting(
                        CsvLoader::getId,
                        CsvLoader::getStatusCsv,
                        CsvLoader::getPath,
                        CsvLoader::getFileName,
                        CsvLoader::getCreatedDate
                )
                .containsExactly(
                        csvLoaderData.getId(),
                        csvLoaderData.getStatusCsv(),
                        csvLoaderData.getPath(),
                        csvLoaderData.getFileName(),
                        csvLoaderData.getCreatedDate()
                );

    }

}
