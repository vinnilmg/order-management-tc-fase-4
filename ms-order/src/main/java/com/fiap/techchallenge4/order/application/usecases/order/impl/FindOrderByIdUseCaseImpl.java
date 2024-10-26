package com.fiap.techchallenge4.order.application.usecases.order.impl;

import com.fiap.techchallenge4.order.application.gateways.order.FindOrderByIdGateway;
import com.fiap.techchallenge4.order.application.usecases.order.FindOrderByIdUseCase;
import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.order.domain.exceptions.NotFoundException;

import static java.util.Objects.isNull;

public class FindOrderByIdUseCaseImpl implements FindOrderByIdUseCase {
    private final FindOrderByIdGateway findOrderByIdGateway;

    public FindOrderByIdUseCaseImpl(FindOrderByIdGateway findOrderByIdGateway) {
        this.findOrderByIdGateway = findOrderByIdGateway;
    }

    @Override
    public Order find(final Long id) {
        if (isNull(id) || id < 0) throw CustomValidationException.of("Order Id", "cannot be null or negative");
        return findOrderByIdGateway.find(id)
                .orElseThrow(() -> NotFoundException.of("Order"));
    }
}
