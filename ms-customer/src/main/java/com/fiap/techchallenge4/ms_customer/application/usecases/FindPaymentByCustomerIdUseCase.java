package com.fiap.techchallenge4.ms_customer.application.usecases;

import com.fiap.techchallenge4.ms_customer.domain.entities.payment.Payment;

@FunctionalInterface
public interface FindPaymentByCustomerIdUseCase {
    Payment find(Long customerId);
}
