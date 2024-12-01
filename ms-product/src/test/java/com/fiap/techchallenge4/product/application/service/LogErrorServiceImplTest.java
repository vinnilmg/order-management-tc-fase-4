package com.fiap.techchallenge4.product.application.service;

import com.fiap.techchallenge4.product.application.controller.mapper.LogErrorMapper;
import com.fiap.techchallenge4.product.application.service.impl.LogErrorServiceImpl;
import com.fiap.techchallenge4.product.core.entity.LogErrorData;
import com.fiap.techchallenge4.product.core.model.LogError;
import com.fiap.techchallenge4.product.core.repository.LogErrorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogErrorServiceImplTest {

    @Mock
    private LogErrorMapper logErrorMapper;

    @Mock
    private LogErrorRepository logErrorRepository;

    @InjectMocks
    private LogErrorServiceImpl logErrorService;


    @Test
    void save_deveChamarMapperERepository() {
        LogError logError = LogError.builder()
                .error("Error log da silva")
                .className("Test")
                .build();

        LogErrorData logErrorEntity = logErrorMapper.toEntity(logError);

        logErrorService.save(logError);

        verify(logErrorRepository, times(1)).save(logErrorEntity);
    }
}
