package com.fiap.techchallenge4.order.application.gateways.order;

import com.fiap.techchallenge4.order.domain.entities.order.Order;

@FunctionalInterface
public interface FinishOrderGateway {
    void finish(Order order);
}
