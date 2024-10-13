package com.fiap.techchallenge4.order.infra.persistence.repositories;

import com.fiap.techchallenge4.order.infra.persistence.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
