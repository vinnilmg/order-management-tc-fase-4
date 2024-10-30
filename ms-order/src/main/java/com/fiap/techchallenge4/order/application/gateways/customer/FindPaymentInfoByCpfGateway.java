package com.fiap.techchallenge4.order.application.gateways.customer;

import com.fiap.techchallenge4.order.domain.entities.customer.payment.Payment;

import java.util.Optional;

@FunctionalInterface
public interface FindPaymentInfoByCpfGateway {
    Optional<Payment> find(String cpf);

}
