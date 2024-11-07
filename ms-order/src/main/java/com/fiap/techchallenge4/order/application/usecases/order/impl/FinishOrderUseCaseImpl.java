package com.fiap.techchallenge4.order.application.usecases.order.impl;

import com.fiap.techchallenge4.order.application.gateways.order.FinishOrderGateway;
import com.fiap.techchallenge4.order.application.usecases.order.FindOrderByIdUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FinishOrderUseCase;
import com.fiap.techchallenge4.order.domain.exceptions.CustomValidationException;

public class FinishOrderUseCaseImpl implements FinishOrderUseCase {
    private final FindOrderByIdUseCase findOrderByIdUseCase;
    private final FinishOrderGateway finishOrderGateway;

    public FinishOrderUseCaseImpl(
            FindOrderByIdUseCase findOrderByIdUseCase,
            FinishOrderGateway finishOrderGateway
    ) {
        this.findOrderByIdUseCase = findOrderByIdUseCase;
        this.finishOrderGateway = finishOrderGateway;
    }

    @Override
    public void finish(final Long orderId) {
        final var order = findOrderByIdUseCase.find(orderId);

        if (!order.isDeliveryRoute()) {
            throw CustomValidationException.of("Order", "is in invalid status to finish");
        }

        order.finish();
        finishOrderGateway.finish(order);
    }
}
