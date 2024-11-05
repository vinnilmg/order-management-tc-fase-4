package com.fiap.techchallenge4.product.application.service.impl;

import com.fiap.techchallenge4.product.application.controller.mapper.LogErrorMapper;
import com.fiap.techchallenge4.product.core.model.LogError;
import com.fiap.techchallenge4.product.core.repository.LogErrorRepository;
import com.fiap.techchallenge4.product.application.service.LogErrorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LogErrorServiceImpl implements LogErrorService {

    private final LogErrorMapper logErrorMapper;

    private final LogErrorRepository logErrorRepository;

    @Override
    public void save(LogError logError){
        logErrorRepository.save(logErrorMapper.toEntity(logError));
    }
}
