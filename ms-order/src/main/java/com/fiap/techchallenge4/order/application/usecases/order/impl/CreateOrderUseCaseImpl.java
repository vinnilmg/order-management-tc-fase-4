package com.fiap.techchallenge4.order.application.usecases.order.impl;

import com.fiap.techchallenge4.order.application.gateways.order.CreateOrderGateway;
import com.fiap.techchallenge4.order.application.gateways.product.FindProductGateway;
import com.fiap.techchallenge4.order.application.usecases.order.CreateOrderUseCase;
import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.infra.gateways.order.OrderKafkaRepositoryGateway;
import com.fiap.techchallenge4.order.infra.gateways.order.OrderRepositoryGateway;

public class CreateOrderUseCaseImpl implements CreateOrderUseCase {
    private final CreateOrderGateway orderRepositoryGateway;
    private final FindProductGateway findProductGateway;
    private final CreateOrderGateway orderKafkaRepositoryGateway;

    public CreateOrderUseCaseImpl(
            OrderRepositoryGateway orderRepositoryGateway,
            FindProductGateway findProductGateway,
            OrderKafkaRepositoryGateway orderKafkaRepositoryGateway
    ) {
        this.orderRepositoryGateway = orderRepositoryGateway;
        this.findProductGateway = findProductGateway;
        this.orderKafkaRepositoryGateway = orderKafkaRepositoryGateway;
    }

    @Override
    public Order execute(final Order order) {
        // Valida se os produtos existem
        /*order.getProducts()
                .forEach(product -> findProductGateway.find(product.getSku())
                        .orElseThrow(() -> CustomValidationException.of("Product", "does not exist")));
        */
        // Deve calcular o frete?

        // Cria o pedido na base
        final var createdOrder = orderRepositoryGateway.create(order);

        // Deve gerar a mensagem no topico
        return orderKafkaRepositoryGateway.create(createdOrder);
    }
}
