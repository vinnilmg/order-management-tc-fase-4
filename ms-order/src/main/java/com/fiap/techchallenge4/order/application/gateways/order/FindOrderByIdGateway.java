package com.fiap.techchallenge4.order.application.gateways.order;

import com.fiap.techchallenge4.order.domain.entities.order.Order;

import java.util.Optional;

@FunctionalInterface
public interface FindOrderByIdGateway {
    Optional<Order> find(Long id);
}
