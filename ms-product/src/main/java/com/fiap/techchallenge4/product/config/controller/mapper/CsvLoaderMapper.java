package com.fiap.techchallenge4.product.config.controller.mapper;

import com.fiap.techchallenge4.product.repository.entity.CsvLoaderData;
import com.fiap.techchallenge4.product.repository.model.CsvLoader;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CsvLoaderMapper {

    private final ModelMapper modelMapper;

    public CsvLoaderData toEntity(CsvLoader csvLoader) {
        TypeMap<CsvLoader, CsvLoaderData> typeMap = modelMapper.getTypeMap(CsvLoader.class, CsvLoaderData.class);
        if (typeMap == null) {
            typeMap = modelMapper.createTypeMap(CsvLoader.class, CsvLoaderData.class);
        }
        return typeMap.map(csvLoader);
    }

    public CsvLoader toModel(CsvLoaderData csvLoaderData) {
        TypeMap<CsvLoaderData, CsvLoader> typeMap = modelMapper.getTypeMap(CsvLoaderData.class, CsvLoader.class);
        if (typeMap == null) {
            typeMap = modelMapper.createTypeMap(CsvLoaderData.class, CsvLoader.class);
        }
        return typeMap.map(csvLoaderData);
    }
}
