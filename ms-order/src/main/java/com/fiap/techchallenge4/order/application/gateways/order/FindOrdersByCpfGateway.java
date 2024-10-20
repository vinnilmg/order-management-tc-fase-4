package com.fiap.techchallenge4.order.application.gateways.order;

import com.fiap.techchallenge4.order.domain.entities.order.Order;

import java.util.List;

@FunctionalInterface
public interface FindOrdersByCpfGateway {
    List<Order> find(String cpf);
}
