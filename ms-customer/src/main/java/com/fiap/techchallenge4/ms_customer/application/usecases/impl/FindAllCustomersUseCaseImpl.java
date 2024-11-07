package com.fiap.techchallenge4.ms_customer.application.usecases.impl;

import com.fiap.techchallenge4.ms_customer.application.gateways.CustomerGateway;
import com.fiap.techchallenge4.ms_customer.application.usecases.FindAllCustomersUseCase;
import com.fiap.techchallenge4.ms_customer.domain.entities.customer.Customer;

import java.util.List;

public class FindAllCustomersUseCaseImpl implements FindAllCustomersUseCase {

    private final CustomerGateway customerGateway;

    public FindAllCustomersUseCaseImpl(CustomerGateway customerGateway) {
        this.customerGateway = customerGateway;
    }

    @Override
    public List<Customer> find() {
        return customerGateway.findAllCustomers();
    }
}
