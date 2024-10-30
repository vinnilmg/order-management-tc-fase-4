package com.fiap.techchallenge4.order.application.usecases.order;

@FunctionalInterface
public interface FinishOrderUseCase {
    void finish(Long orderId);
}
