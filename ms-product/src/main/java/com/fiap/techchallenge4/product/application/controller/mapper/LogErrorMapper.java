package com.fiap.techchallenge4.product.application.controller.mapper;

import com.fiap.techchallenge4.product.core.entity.LogErrorData;
import com.fiap.techchallenge4.product.core.model.LogError;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LogErrorMapper {

    private final ModelMapper modelMapper;

    public LogErrorData toEntity(LogError logError) {
        TypeMap<LogError, LogErrorData> typeMap = modelMapper.getTypeMap(LogError.class, LogErrorData.class);
        if (typeMap == null) {
            typeMap = modelMapper.createTypeMap(LogError.class, LogErrorData.class);
        }
        return typeMap.map(logError);
    }

    public LogError toModel(LogErrorData logErrorData) {
        TypeMap<LogErrorData, LogError> typeMap = modelMapper.getTypeMap(LogErrorData.class, LogError.class);
        if (typeMap == null) {
            typeMap = modelMapper.createTypeMap(LogErrorData.class, LogError.class);
        }
        return typeMap.map(logErrorData);
    }
}
