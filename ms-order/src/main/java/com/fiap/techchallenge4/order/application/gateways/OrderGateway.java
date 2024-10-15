package com.fiap.techchallenge4.order.application.gateways;

import com.fiap.techchallenge4.order.domain.entities.Order;

import java.util.List;
import java.util.Optional;

public interface OrderGateway {
    Optional<Order> findOrderById(Long id);

    List<Order> findAllOrders();

    List<Order> findOrdersByCpf(String cpf);
}
