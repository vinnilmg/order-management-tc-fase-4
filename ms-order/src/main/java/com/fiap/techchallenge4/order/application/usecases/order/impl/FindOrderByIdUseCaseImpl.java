package com.fiap.techchallenge4.order.application.usecases.order.impl;

import com.fiap.techchallenge4.order.application.gateways.OrderGateway;
import com.fiap.techchallenge4.order.application.usecases.order.FindOrderByIdUseCase;
import com.fiap.techchallenge4.order.domain.entities.Order;
import com.fiap.techchallenge4.order.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.order.domain.exceptions.NotFoundException;

import static java.util.Objects.isNull;

public class FindOrderByIdUseCaseImpl implements FindOrderByIdUseCase {
    private final OrderGateway orderGateway;

    public FindOrderByIdUseCaseImpl(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    @Override
    public Order find(final Long id) {
        if (isNull(id) || id < 0) throw CustomValidationException.of("Order Id", "cannot be null or negative");
        return orderGateway.findOrderById(id)
                .orElseThrow(() -> NotFoundException.of("Order"));
    }
}
