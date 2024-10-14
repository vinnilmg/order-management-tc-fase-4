package com.fiap.techchallenge4.order.application.gateways;

import com.fiap.techchallenge4.order.domain.entities.Order;

import java.util.Optional;

public interface OrderGateway {
    Optional<Order> findOrderById(Long id);
}
