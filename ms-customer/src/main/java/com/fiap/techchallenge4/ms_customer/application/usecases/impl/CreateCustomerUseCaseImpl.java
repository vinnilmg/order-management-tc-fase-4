package com.fiap.techchallenge4.ms_customer.application.usecases.impl;

import com.fiap.techchallenge4.ms_customer.application.gateways.CustomerGateway;
import com.fiap.techchallenge4.ms_customer.application.usecases.CreateCustomerUseCase;
import com.fiap.techchallenge4.ms_customer.domain.entities.customer.Customer;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.CustomValidationException;

public class CreateCustomerUseCaseImpl implements CreateCustomerUseCase {
    private final CustomerGateway customerGateway;

    public CreateCustomerUseCaseImpl(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
    }

    @Override
    public Customer execute(final Customer customer) {
        var cpf = customerGateway.findCustomerByCpf(customer.getCpf());

        if (cpf.isPresent()) {
            throw CustomValidationException.of("CPF", "already exists");
        }

        return customerGateway.create(customer);
    }
}
