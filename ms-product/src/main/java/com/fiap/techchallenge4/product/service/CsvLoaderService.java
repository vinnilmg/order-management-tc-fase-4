package com.fiap.techchallenge4.product.service;

import com.fiap.techchallenge4.product.repository.model.CsvLoader;

import java.util.List;

public interface CsvLoaderService {

    List<CsvLoader> findAllByStatusPending();

    List<CsvLoader> findAllByStatusWaiting();

    void saveAll(List<CsvLoader> csvLoaderList);

    CsvLoader save(CsvLoader csvLoader);

}
