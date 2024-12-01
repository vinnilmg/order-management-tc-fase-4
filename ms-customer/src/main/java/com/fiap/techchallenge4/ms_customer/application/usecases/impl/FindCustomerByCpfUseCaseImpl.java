package com.fiap.techchallenge4.ms_customer.application.usecases.impl;

import com.fiap.techchallenge4.ms_customer.application.gateways.CustomerGateway;
import com.fiap.techchallenge4.ms_customer.application.usecases.FindCustomerByCpfUseCase;
import com.fiap.techchallenge4.ms_customer.domain.entities.customer.Customer;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.NotFoundException;

import java.util.Optional;

import static java.util.Objects.isNull;

public class FindCustomerByCpfUseCaseImpl implements FindCustomerByCpfUseCase {
    private final CustomerGateway customerGateway;

    public FindCustomerByCpfUseCaseImpl(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
    }

    @Override
    public Customer find(final String cpf) {
        if (isNull(cpf) || cpf.length() != 11) throw CustomValidationException.of("Customer CPF",
                "invalid");
        return customerGateway.findCustomerByCpf(cpf)
                .orElseThrow(() -> NotFoundException.of("Customer"));
    }
}
