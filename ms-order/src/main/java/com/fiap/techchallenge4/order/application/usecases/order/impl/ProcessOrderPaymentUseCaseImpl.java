package com.fiap.techchallenge4.order.application.usecases.order.impl;

import com.fiap.techchallenge4.order.application.gateways.customer.FindPaymentInfoByCpfGateway;
import com.fiap.techchallenge4.order.application.usecases.order.ProcessOrderPaymentUseCase;
import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.domain.exceptions.NotFoundException;

public class ProcessOrderPaymentUseCaseImpl implements ProcessOrderPaymentUseCase {
    private final FindPaymentInfoByCpfGateway findPaymentInfoByCpfGateway;

    public ProcessOrderPaymentUseCaseImpl(FindPaymentInfoByCpfGateway findPaymentInfoByCpfGateway) {
        this.findPaymentInfoByCpfGateway = findPaymentInfoByCpfGateway;
    }

    @Override
    public void process(final Order order) {
        final var payment = findPaymentInfoByCpfGateway.find(order.getCpf())
                .orElseThrow(NotFoundException::ofPayment);


    }
}
