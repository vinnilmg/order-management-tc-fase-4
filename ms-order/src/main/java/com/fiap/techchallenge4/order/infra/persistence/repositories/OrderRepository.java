package com.fiap.techchallenge4.order.infra.persistence.repositories;

import com.fiap.techchallenge4.order.infra.persistence.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
