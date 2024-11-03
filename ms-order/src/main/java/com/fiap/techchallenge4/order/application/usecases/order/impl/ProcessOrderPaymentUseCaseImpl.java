package com.fiap.techchallenge4.order.application.usecases.order.impl;

import com.fiap.techchallenge4.order.application.gateways.customer.FindPaymentInfoByCpfGateway;
import com.fiap.techchallenge4.order.application.gateways.order.CreateOrderGateway;
import com.fiap.techchallenge4.order.application.gateways.order.UpdateOrderStatusGateway;
import com.fiap.techchallenge4.order.application.gateways.payment.ProcessPaymentGateway;
import com.fiap.techchallenge4.order.application.gateways.product.AddStockGateway;
import com.fiap.techchallenge4.order.application.usecases.order.ProcessOrderPaymentUseCase;
import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.domain.exceptions.NotFoundException;
import com.fiap.techchallenge4.order.infra.gateways.order.CreateProcessedOrderKafkaRepositoryGateway;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProcessOrderPaymentUseCaseImpl implements ProcessOrderPaymentUseCase {
    private final FindPaymentInfoByCpfGateway findPaymentInfoByCpfGateway;
    private final UpdateOrderStatusGateway updateOrderStatusGateway;
    private final CreateOrderGateway createProcessedOrderKafkaGateway;
    private final ProcessPaymentGateway processPaymentGateway;
    private final AddStockGateway addStockGateway;

    public ProcessOrderPaymentUseCaseImpl(
            FindPaymentInfoByCpfGateway findPaymentInfoByCpfGateway,
            UpdateOrderStatusGateway updateOrderStatusGateway,
            CreateProcessedOrderKafkaRepositoryGateway createProcessedOrderKafkaGateway,
            ProcessPaymentGateway processPaymentGateway, AddStockGateway addStockGateway
    ) {
        this.findPaymentInfoByCpfGateway = findPaymentInfoByCpfGateway;
        this.updateOrderStatusGateway = updateOrderStatusGateway;
        this.createProcessedOrderKafkaGateway = createProcessedOrderKafkaGateway;
        this.processPaymentGateway = processPaymentGateway;
        this.addStockGateway = addStockGateway;
    }

    @Override
    public void process(final Order order) {
        final var payment = findPaymentInfoByCpfGateway.find(order.getCpf())
                .orElseThrow(NotFoundException::ofPayment);

        final var paymentApproved = processPaymentGateway.process(payment);

        if (paymentApproved) {
            order.updateToProcessed();
        } else {
            log.info("Pagamento não aprovado, o pedido será cancelado.");

            order.getProducts()
                    .forEach(product -> addStockGateway.add(product.getSku(), product.getQuantity()));

            order.updateToCanceled();
        }

        updateOrderStatusGateway.update(order);

        if (paymentApproved) createProcessedOrderKafkaGateway.create(order);
    }
}
