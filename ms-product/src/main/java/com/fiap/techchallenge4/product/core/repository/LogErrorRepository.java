package com.fiap.techchallenge4.product.core.repository;

import com.fiap.techchallenge4.product.core.entity.LogErrorData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogErrorRepository extends JpaRepository<LogErrorData, Long> {

}
