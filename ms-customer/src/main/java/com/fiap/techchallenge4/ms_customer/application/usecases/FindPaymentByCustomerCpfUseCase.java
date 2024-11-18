package com.fiap.techchallenge4.ms_customer.application.usecases;

import com.fiap.techchallenge4.ms_customer.domain.entities.payment.Payment;

@FunctionalInterface
public interface FindPaymentByCustomerCpfUseCase {
    Payment find(String cpf);
}
