package com.fiap.techchallenge4.order.application.usecases.order;

import com.fiap.techchallenge4.order.domain.entities.order.Order;

@FunctionalInterface
public interface ProcessOrderPaymentUseCase {
    void process(Order order);
}
