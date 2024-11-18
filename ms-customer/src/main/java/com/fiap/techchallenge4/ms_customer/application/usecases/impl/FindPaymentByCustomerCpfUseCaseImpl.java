package com.fiap.techchallenge4.ms_customer.application.usecases.impl;

import com.fiap.techchallenge4.ms_customer.application.gateways.PaymentGateway;
import com.fiap.techchallenge4.ms_customer.application.usecases.FindPaymentByCustomerCpfUseCase;
import com.fiap.techchallenge4.ms_customer.domain.entities.payment.Payment;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.NotFoundException;

import static java.util.Objects.isNull;

public class FindPaymentByCustomerCpfUseCaseImpl implements FindPaymentByCustomerCpfUseCase {
    private final PaymentGateway paymentGateway;

    public FindPaymentByCustomerCpfUseCaseImpl(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }


    @Override
    public Payment find(final String cpf) {
        if (isNull(cpf) || cpf.length() != 11) throw CustomValidationException.of("Customer CPF",
                "invalid");
        return paymentGateway.findPaymentByCustomerCpf(cpf)
                .orElseThrow(() -> NotFoundException.of("Customer Payment"));
    }
}
