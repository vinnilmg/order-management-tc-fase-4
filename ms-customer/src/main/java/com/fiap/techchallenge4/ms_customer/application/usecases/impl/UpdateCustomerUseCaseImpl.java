package com.fiap.techchallenge4.ms_customer.application.usecases.impl;

import com.fiap.techchallenge4.ms_customer.application.gateways.AddressGateway;
import com.fiap.techchallenge4.ms_customer.application.gateways.CustomerGateway;
import com.fiap.techchallenge4.ms_customer.application.usecases.FindCustomerByIdUseCase;
import com.fiap.techchallenge4.ms_customer.application.usecases.UpdateCustomerUseCase;
import com.fiap.techchallenge4.ms_customer.domain.entities.customer.Customer;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.NotFoundException;
import com.fiap.techchallenge4.ms_customer.infra.controllers.mappers.CustomerRequestMapper;
import com.fiap.techchallenge4.ms_customer.infra.controllers.request.CustomerRequest;
import com.fiap.techchallenge4.ms_customer.infra.persistence.entities.CustomerEntity;
import com.fiap.techchallenge4.ms_customer.infra.persistence.mappers.CustomerEntityMapper;
import com.fiap.techchallenge4.ms_customer.infra.persistence.mappers.CustomerEntityMapperImpl;

import static java.util.Objects.isNull;

public class UpdateCustomerUseCaseImpl implements UpdateCustomerUseCase {
    private final CustomerGateway customerGateway;
    private final AddressGateway addressGateway;
    private final FindCustomerByIdUseCase findCustomerByIdUseCase;

    public UpdateCustomerUseCaseImpl(CustomerGateway customerGateway,
                                     AddressGateway addressGateway,
                                     FindCustomerByIdUseCase findCustomerByIdUseCase) {
        this.customerGateway = customerGateway;
        this.addressGateway = addressGateway;
        this.findCustomerByIdUseCase = findCustomerByIdUseCase;
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
