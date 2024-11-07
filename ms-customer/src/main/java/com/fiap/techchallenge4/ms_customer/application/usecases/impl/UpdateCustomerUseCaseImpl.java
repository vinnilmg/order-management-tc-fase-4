package com.fiap.techchallenge4.ms_customer.application.usecases.impl;

import com.fiap.techchallenge4.ms_customer.application.gateways.CustomerGateway;
import com.fiap.techchallenge4.ms_customer.application.usecases.UpdateCustomerUseCase;
import com.fiap.techchallenge4.ms_customer.domain.entities.customer.Customer;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.NotFoundException;

import static java.util.Objects.isNull;

public class UpdateCustomerUseCaseImpl implements UpdateCustomerUseCase {
    private final CustomerGateway customerGateway;

    public UpdateCustomerUseCaseImpl(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
    }

    @Override
    public void execute(Long customerId, Customer updatedCustomer) {
        if (isNull(customerId) || customerId < 0) throw CustomValidationException.of("Customer Id",
                "cannot be null or negative");
        if (isNull(updatedCustomer)) throw CustomValidationException.of("Customer", "cannot be null");

       customerGateway.findCustomerById(customerId)
                .orElseThrow(() -> NotFoundException.of("Customer"));

        updatedCustomer.setId(customerId);
        customerGateway.update(updatedCustomer);
    }
}
