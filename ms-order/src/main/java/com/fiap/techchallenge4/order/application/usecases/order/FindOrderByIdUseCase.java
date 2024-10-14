package com.fiap.techchallenge4.order.application.usecases.order;

import com.fiap.techchallenge4.order.domain.entities.Order;

@FunctionalInterface
public interface FindOrderByIdUseCase {
    Order find(Long id);
}
