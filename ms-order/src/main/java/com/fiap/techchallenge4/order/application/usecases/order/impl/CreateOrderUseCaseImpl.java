package com.fiap.techchallenge4.order.application.usecases.order.impl;

import com.fiap.techchallenge4.order.application.gateways.customer.FindCustomerByCpfGateway;
import com.fiap.techchallenge4.order.application.gateways.order.CreateOrderGateway;
import com.fiap.techchallenge4.order.application.gateways.product.DecreaseStockGateway;
import com.fiap.techchallenge4.order.application.gateways.product.FindProductGateway;
import com.fiap.techchallenge4.order.application.gateways.shipping.SimulateShippingGateway;
import com.fiap.techchallenge4.order.application.usecases.order.CreateOrderUseCase;
import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.order.domain.exceptions.NotFoundException;
import com.fiap.techchallenge4.order.infra.gateways.order.CreateOrderKafkaRepositoryGateway;
import com.fiap.techchallenge4.order.infra.gateways.order.OrderRepositoryGateway;

public class CreateOrderUseCaseImpl implements CreateOrderUseCase {
    private final CreateOrderGateway createOrderDatabaseGateway;
    private final FindProductGateway findProductGateway;
    private final CreateOrderGateway createOrderKafkaGateway;
    private final DecreaseStockGateway decreaseStockGateway;
    private final FindCustomerByCpfGateway findCustomerByCpfGateway;
    private final SimulateShippingGateway simulateShippingGateway;

    public CreateOrderUseCaseImpl(
            OrderRepositoryGateway createOrderDatabaseGateway,
            FindProductGateway findProductGateway,
            CreateOrderKafkaRepositoryGateway createOrderKafkaGateway,
            DecreaseStockGateway decreaseStockGateway,
            FindCustomerByCpfGateway findCustomerByCpfGateway,
            SimulateShippingGateway simulateShippingGateway
    ) {
        this.createOrderDatabaseGateway = createOrderDatabaseGateway;
        this.findProductGateway = findProductGateway;
        this.createOrderKafkaGateway = createOrderKafkaGateway;
        this.decreaseStockGateway = decreaseStockGateway;
        this.findCustomerByCpfGateway = findCustomerByCpfGateway;
        this.simulateShippingGateway = simulateShippingGateway;
    }

    @Override
    public Order execute(final Order order) {
        // Busca o cliente
        final var customer = findCustomerByCpfGateway.find(order.getCpf())
                .orElseThrow(NotFoundException::ofCustomer);

        // Busca o produto, valida se a quantidade informada existe em estoque e se o valor é igual
        order.getProducts()
                .forEach(product -> {
                    final var providerProduct = findProductGateway.find(product.getSku())
                            .orElseThrow(NotFoundException::ofProduct);

                    if (product.getQuantity() > providerProduct.getQuantity()) {
                        throw CustomValidationException.of("Product", "without stock");
                    }

                    if (product.getValue().compareTo(providerProduct.getValue()) != 0) {
                        throw CustomValidationException.of("Product", "contain different value");
                    }

                    // Diminui do estoque
                    decreaseStockGateway.decrease(product.getSku(), product.getQuantity());
                });

        // Busca o valor de frete para o CEP do cliente
        final var shipping = simulateShippingGateway.simulate(customer.getAddress().getPostalCode());
        order.updateShippingInfo(shipping);

        // Cria o pedido na base
        final var createdOrder = createOrderDatabaseGateway.create(order);

        // Gera a mensagem no topico
        return createOrderKafkaGateway.create(createdOrder);
    }
}
