package com.fiap.techchallenge4.ms_customer.application.usecases.impl;

import com.fiap.techchallenge4.ms_customer.application.gateways.AddressGateway;
import com.fiap.techchallenge4.ms_customer.application.gateways.CustomerGateway;
import com.fiap.techchallenge4.ms_customer.application.usecases.CreateCustomerUseCase;
import com.fiap.techchallenge4.ms_customer.domain.entities.customer.Customer;

public class CreateCustomerUseCaseImpl implements CreateCustomerUseCase {
    private final CustomerGateway customerGateway;
    private final AddressGateway addressGateway;

    public CreateCustomerUseCaseImpl(CustomerGateway customerGateway,
                                     AddressGateway addressGateway) {
        this.customerGateway = customerGateway;
        this.addressGateway = addressGateway;
    }

    @Override
    public Customer execute(final Customer customer) {
        addressGateway.create(customer.getAddress());
        return customerGateway.create(customer);
    }
}
