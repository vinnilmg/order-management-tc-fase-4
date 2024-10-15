package com.fiap.techchallenge4.order.application.usecases.order;

import com.fiap.techchallenge4.order.domain.entities.Order;

import java.util.List;

@FunctionalInterface
public interface FindAllOrdersUseCase {
    List<Order> find();
}
