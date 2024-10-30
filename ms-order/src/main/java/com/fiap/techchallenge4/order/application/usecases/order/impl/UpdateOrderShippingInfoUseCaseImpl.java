package com.fiap.techchallenge4.order.application.usecases.order.impl;

import com.fiap.techchallenge4.order.application.gateways.order.UpdateOrderStatusGateway;
import com.fiap.techchallenge4.order.application.usecases.order.FindOrderByIdUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.UpdateOrderShippingInfoUseCase;
import com.fiap.techchallenge4.order.domain.enums.OrderStatusEnum;
import com.fiap.techchallenge4.order.domain.exceptions.CustomValidationException;

public class UpdateOrderShippingInfoUseCaseImpl implements UpdateOrderShippingInfoUseCase {
    private final FindOrderByIdUseCase findOrderByIdUseCase;
    private final UpdateOrderStatusGateway updateOrderStatusGateway;

    public UpdateOrderShippingInfoUseCaseImpl(
            FindOrderByIdUseCase findOrderByIdUseCase,
            UpdateOrderStatusGateway updateOrderStatusGateway
    ) {
        this.findOrderByIdUseCase = findOrderByIdUseCase;
        this.updateOrderStatusGateway = updateOrderStatusGateway;
    }

    @Override
    public void update(final Long orderId) {
        final var order = findOrderByIdUseCase.find(orderId);

        if (!order.getStatusEnum().equals(OrderStatusEnum.AGUARDANDO_ENVIO)) {
            throw CustomValidationException.of("Order", "is in invalid status to update");
        }

        order.updateToDeliveryRoute();
        updateOrderStatusGateway.update(order);
    }
}
