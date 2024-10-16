package com.fiap.techchallenge4.order.application.usecases.order.impl;

import com.fiap.techchallenge4.order.application.gateways.OrderGateway;
import com.fiap.techchallenge4.order.application.usecases.order.FindAllOrdersUseCase;
import com.fiap.techchallenge4.order.domain.entities.order.Order;

import java.util.List;

public class FindAllOrdersUseCaseImpl implements FindAllOrdersUseCase {
    private final OrderGateway orderGateway;

    public FindAllOrdersUseCaseImpl(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    @Override
    public List<Order> find() {
        return orderGateway.findAllOrders();
    }
}
