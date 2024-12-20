package com.fiap.techchallenge4.product.application.service.impl;

import com.fiap.techchallenge4.product.application.controller.mapper.CsvLoaderMapper;
import com.fiap.techchallenge4.product.application.exception.NotFoundException;
import com.fiap.techchallenge4.product.core.repository.CsvLoaderRepository;
import com.fiap.techchallenge4.product.core.entity.CsvLoaderData;
import com.fiap.techchallenge4.product.core.enums.StatusCsv;
import com.fiap.techchallenge4.product.core.model.CsvLoader;
import com.fiap.techchallenge4.product.application.service.CsvLoaderService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        csvLoaderRepository.saveAllAndFlush(csvLoaderList.stream()
                .map(csvLoaderMapper::toEntity)
                .toList()
        );
    }

    @Override
    @Transactional
    public CsvLoader save(CsvLoader csvLoader) {
        CsvLoaderData entity = csvLoaderMapper.toEntity(csvLoader);
        CsvLoaderData savedEntity = csvLoaderRepository.saveAndFlush(entity);
        return csvLoaderMapper.toModel(savedEntity);
    }
}
