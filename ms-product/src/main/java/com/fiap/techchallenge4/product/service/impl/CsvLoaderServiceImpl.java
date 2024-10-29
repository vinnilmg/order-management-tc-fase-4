package com.fiap.techchallenge4.product.service.impl;

import com.fiap.techchallenge4.product.config.controller.mapper.CsvLoaderMapper;
import com.fiap.techchallenge4.product.repository.CsvLoaderRepository;
import com.fiap.techchallenge4.product.repository.entity.CsvLoaderData;
import com.fiap.techchallenge4.product.repository.enums.StatusCsv;
import com.fiap.techchallenge4.product.repository.model.CsvLoader;
import com.fiap.techchallenge4.product.service.CsvLoaderService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CsvLoaderServiceImpl implements CsvLoaderService {

    private final CsvLoaderRepository csvLoaderRepository;

    private final CsvLoaderMapper csvLoaderMapper;

    @Override
    public List<CsvLoader> findAllByStatusPending() {
        return csvLoaderRepository.findAllByStatusCsv(StatusCsv.PENDING)
                .stream()
                .map(csvLoaderMapper::toModel)
                .toList();
    }

    @Override
    public List<CsvLoader> findAllByStatusWaiting() {
        return csvLoaderRepository.findAllByStatusCsv(StatusCsv.WAITING)
                .stream()
                .map(csvLoaderMapper::toModel)
                .toList();
    }

    @Override
    public void saveAll(List<CsvLoader> csvLoaderList) {

        var csvLoaderToSave = csvLoaderList.stream()
                .map(csvLoaderMapper::toEntity)
                .toList();

        csvLoaderRepository.saveAndFlush(csvLoaderToSave.get(0));
    }

    @Override
    @Transactional
    public CsvLoader save(CsvLoader csvLoader) {
        CsvLoaderData entity = csvLoaderMapper.toEntity(csvLoader);
        CsvLoaderData savedEntity = csvLoaderRepository.saveAndFlush(entity); // Use save() if flush isn't needed
        return csvLoaderMapper.toModel(savedEntity);
    }
}
