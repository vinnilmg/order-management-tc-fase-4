package com.fiap.techchallenge4.product.repository;

import com.fiap.techchallenge4.product.entity.CsvLoaderData;
import com.fiap.techchallenge4.product.enums.StatusCsv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CsvLoaderRepository extends JpaRepository<CsvLoaderData, Long> {

    List<CsvLoaderData> findAllByStatusCsv(StatusCsv statusCsv);
}
