package com.fiap.techchallenge4.product.application.controller.mapper;

import com.fiap.techchallenge4.product.core.entity.LogErrorData;
import com.fiap.techchallenge4.product.core.model.LogError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class LogErrorMapperTest {

    private LogErrorMapper logErrorMapper;

    @BeforeEach
    void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        logErrorMapper = new LogErrorMapper(modelMapper);
    }

    @Test
    void testToEntity() {
        // Arrange
        LogError logError = LogError.builder()
                .error("Test Error Data Message")
                .id(1L)
                .className("Teste")
                .idEntity(1L)
                .createdDate(LocalDateTime.now())
                .build();
        // Act
        LogErrorData logErrorData = logErrorMapper.toEntity(logError);

        // Assert
        assertThat(logError)
                .isNotNull()
                .extracting(
                        LogError::getError,
                        LogError::getClassName,
                        LogError::getId,
                        LogError::getIdEntity
                )
                .containsExactly(
                        logErrorData.getError(),
                        logErrorData.getClassName(),
                        logErrorData.getId(),
                        logErrorData.getIdEntity()
                );
    }

    @Test
    void testToModel() {
        // Arrange
        LogErrorData logErrorData = LogErrorData.builder()
                .error("Test Error Data Message")
                .id(1L)
                .className("Teste")
                .idEntity(1L)
                .createdDate(LocalDateTime.now())
                .build();

        // Act
        LogError logError = logErrorMapper.toModel(logErrorData);

        // Assert
        assertThat(logError)
                .isNotNull()
                .extracting(
                        LogError::getError,
                        LogError::getClassName,
                        LogError::getId,
                        LogError::getIdEntity
                )
                .containsExactly(
                        logErrorData.getError(),
                        logErrorData.getClassName(),
                        logErrorData.getId(),
                        logErrorData.getIdEntity()
                );

    }
}
