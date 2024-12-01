package com.fiap.techchallenge4.product.application.service;

import com.fiap.techchallenge4.product.core.model.CsvLoader;

import java.util.List;

public interface CsvLoaderService {

    List<CsvLoader> findAllByStatusPending();

    List<CsvLoader> findAllByStatusWaiting();

    void saveAll(List<CsvLoader> csvLoaderList);

    CsvLoader save(CsvLoader csvLoader);

}
