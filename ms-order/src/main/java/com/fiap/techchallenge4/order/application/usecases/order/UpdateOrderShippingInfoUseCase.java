package com.fiap.techchallenge4.order.application.usecases.order;

@FunctionalInterface
public interface UpdateOrderShippingInfoUseCase {
    void update(Long orderId);
}
