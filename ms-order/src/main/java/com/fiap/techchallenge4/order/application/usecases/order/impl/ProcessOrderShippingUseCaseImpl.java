package com.fiap.techchallenge4.order.application.usecases.order.impl;

import com.fiap.techchallenge4.order.application.gateways.customer.FindCustomerByCpfGateway;
import com.fiap.techchallenge4.order.application.gateways.order.UpdateOrderStatusGateway;
import com.fiap.techchallenge4.order.application.gateways.shipping.CreateShippingGateway;
import com.fiap.techchallenge4.order.application.usecases.order.ProcessOrderShippingUseCase;
import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.order.domain.exceptions.NotFoundException;

public class ProcessOrderShippingUseCaseImpl implements ProcessOrderShippingUseCase {
    private final FindCustomerByCpfGateway findCustomerByCpfGateway;
    private final CreateShippingGateway createShippingGateway;
    private final UpdateOrderStatusGateway updateOrderStatusGateway;

    public ProcessOrderShippingUseCaseImpl(
            FindCustomerByCpfGateway findCustomerByCpfGateway,
            CreateShippingGateway createShippingGateway,
            UpdateOrderStatusGateway updateOrderStatusGateway
    ) {
        this.findCustomerByCpfGateway = findCustomerByCpfGateway;
        this.createShippingGateway = createShippingGateway;
        this.updateOrderStatusGateway = updateOrderStatusGateway;
    }

    @Override
    public void process(final Order order) {
        if (!order.isProcessed()) {
            throw CustomValidationException.of("Order", "is in invalid status to process shipping");
        }

        final var customer = findCustomerByCpfGateway.find(order.getCpf())
                .orElseThrow(NotFoundException::ofCustomer);

        createShippingGateway.create(order.getId(), customer.getAddress().getPostalCode());

        order.updateToWaitShipping();
        updateOrderStatusGateway.update(order);
    }
}
