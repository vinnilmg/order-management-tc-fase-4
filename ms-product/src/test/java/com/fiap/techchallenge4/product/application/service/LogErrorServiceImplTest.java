package com.fiap.techchallenge4.product.application.service;

import com.fiap.techchallenge4.product.application.controller.mapper.LogErrorMapper;
import com.fiap.techchallenge4.product.application.service.impl.LogErrorServiceImpl;
import com.fiap.techchallenge4.product.core.entity.LogErrorData;
import com.fiap.techchallenge4.product.core.model.LogError;
import com.fiap.techchallenge4.product.core.repository.LogErrorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class LogErrorServiceImplTest {

    @Mock
    private LogErrorMapper logErrorMapper;

    @Mock
    private LogErrorRepository logErrorRepository;

    @InjectMocks
    private LogErrorServiceImpl logErrorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_deveChamarMapperERepository() {
        // Arrange
        LogError logError = LogError.builder()
                .error("Error log da silva")
                .className("Test")
                .build();

        LogErrorData logErrorEntity = logErrorMapper.toEntity(logError);

        // Configura o mock do mapeamento

        // Act
        logErrorService.save(logError);

        // Assert
        verify(logErrorRepository, times(1)).save(logErrorEntity); // Verifica se o repositório foi chamado com a entidade correta
    }
}
