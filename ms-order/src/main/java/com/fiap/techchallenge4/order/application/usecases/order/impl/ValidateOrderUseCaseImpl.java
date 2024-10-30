package com.fiap.techchallenge4.order.application.usecases.order.impl;

import com.fiap.techchallenge4.order.application.gateways.order.CreateOrderGateway;
import com.fiap.techchallenge4.order.application.gateways.order.UpdateOrderStatusGateway;
import com.fiap.techchallenge4.order.application.usecases.order.ValidateOrderUseCase;
import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.domain.validator.OrderDomainValidate;
import com.fiap.techchallenge4.order.infra.gateways.order.CreateOrderPendingPaymentKafkaRepositoryGateway;

public class ValidateOrderUseCaseImpl implements ValidateOrderUseCase {
    private final UpdateOrderStatusGateway updateOrderStatusGateway;
    private final CreateOrderGateway createOrderPendingPaymentKafkaGateway;

    public ValidateOrderUseCaseImpl(UpdateOrderStatusGateway updateOrderStatusGateway, CreateOrderPendingPaymentKafkaRepositoryGateway createOrderPendingPaymentKafkaGateway) {
        this.updateOrderStatusGateway = updateOrderStatusGateway;
        this.createOrderPendingPaymentKafkaGateway = createOrderPendingPaymentKafkaGateway;
    }

    @Override
    public void validate(final Order order) {
        final var isValid = OrderDomainValidate.validate(order);

        if (isValid) {
            order.updateToPendingPayment();
            updateOrderStatusGateway.update(order);
            createOrderPendingPaymentKafkaGateway.create(order);
        }
    }
}
