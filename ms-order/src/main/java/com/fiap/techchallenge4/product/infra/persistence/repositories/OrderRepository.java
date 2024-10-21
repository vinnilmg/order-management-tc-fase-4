package com.fiap.techchallenge4.product.infra.persistence.repositories;

import com.fiap.techchallenge4.product.infra.persistence.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
