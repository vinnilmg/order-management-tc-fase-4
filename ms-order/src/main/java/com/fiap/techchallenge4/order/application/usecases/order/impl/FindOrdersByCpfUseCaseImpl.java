package com.fiap.techchallenge4.order.application.usecases.order.impl;

import com.fiap.techchallenge4.order.application.gateways.OrderGateway;
import com.fiap.techchallenge4.order.application.usecases.order.FindOrdersByCpfUseCase;
import com.fiap.techchallenge4.order.domain.entities.Order;
import com.fiap.techchallenge4.order.domain.exceptions.CustomValidationException;

import java.util.List;

import static java.util.Objects.isNull;

public class FindOrdersByCpfUseCaseImpl implements FindOrdersByCpfUseCase {
    private final OrderGateway orderGateway;

    public FindOrdersByCpfUseCaseImpl(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    @Override
    public List<Order> find(final String cpf) {
        if (isNull(cpf) || cpf.length() != 11) throw CustomValidationException.of("Customer CPF", "is invalid");
        return orderGateway.findOrdersByCpf(cpf);
    }
}
