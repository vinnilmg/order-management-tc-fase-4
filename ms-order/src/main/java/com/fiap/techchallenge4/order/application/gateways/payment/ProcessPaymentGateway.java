package com.fiap.techchallenge4.order.application.gateways.payment;

import com.fiap.techchallenge4.order.domain.entities.customer.payment.Payment;

@FunctionalInterface
public interface ProcessPaymentGateway {
    boolean process(Payment payment);
}
