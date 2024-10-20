package com.fiap.techchallenge4.order.application.usecases.order.impl;

import com.fiap.techchallenge4.order.application.gateways.order.FindAllOrdersGateway;
import com.fiap.techchallenge4.order.application.usecases.order.FindAllOrdersUseCase;
import com.fiap.techchallenge4.order.domain.entities.order.Order;

import java.util.List;

public class FindAllOrdersUseCaseImpl implements FindAllOrdersUseCase {
    private final FindAllOrdersGateway findAllOrdersGateway;

    public FindAllOrdersUseCaseImpl(FindAllOrdersGateway findAllOrdersGateway) {
        this.findAllOrdersGateway = findAllOrdersGateway;
    }

    @Override
    public List<Order> find() {
        return findAllOrdersGateway.find();
    }
}
