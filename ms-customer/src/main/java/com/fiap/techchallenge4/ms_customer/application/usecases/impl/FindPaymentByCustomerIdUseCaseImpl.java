package com.fiap.techchallenge4.ms_customer.application.usecases.impl;

import com.fiap.techchallenge4.ms_customer.application.gateways.PaymentGateway;
import com.fiap.techchallenge4.ms_customer.application.usecases.FindPaymentByCustomerIdUseCase;
import com.fiap.techchallenge4.ms_customer.domain.entities.payment.Payment;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.NotFoundException;

import static java.util.Objects.isNull;

public class FindPaymentByCustomerIdUseCaseImpl implements FindPaymentByCustomerIdUseCase {
    private final PaymentGateway paymentGateway;

    public FindPaymentByCustomerIdUseCaseImpl(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }


    @Override
    public Payment find(final Long customerId) {
        if (isNull(customerId) || customerId < 0) throw CustomValidationException.of("Customer Id",
                "cannot be null or negative");
        return paymentGateway.findPaymentByCustomerId(customerId)
                .orElseThrow(() -> NotFoundException.of("Customer Payment"));
    }
}
