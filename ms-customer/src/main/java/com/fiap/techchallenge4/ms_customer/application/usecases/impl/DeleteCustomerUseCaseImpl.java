package com.fiap.techchallenge4.ms_customer.application.usecases.impl;

import com.fiap.techchallenge4.ms_customer.application.gateways.CustomerGateway;
import com.fiap.techchallenge4.ms_customer.application.usecases.DeleteCustomerUseCase;
import com.fiap.techchallenge4.ms_customer.application.usecases.FindCustomerByIdUseCase;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.NotFoundException;

import static java.util.Objects.isNull;

public class DeleteCustomerUseCaseImpl implements DeleteCustomerUseCase {
    private final CustomerGateway customerGateway;
    private final FindCustomerByIdUseCase findCustomerByIdUseCase;

    public DeleteCustomerUseCaseImpl(CustomerGateway customerGateway,
                                     FindCustomerByIdUseCase findCustomerByIdUseCase) {
        this.customerGateway = customerGateway;
        this.findCustomerByIdUseCase = findCustomerByIdUseCase;
    }

    @Override
    public void execute(final Long id) {
        if (isNull(id) || id < 0) throw CustomValidationException.of("Customer Id",
                "cannot be null or negative");
        final var customer = findCustomerByIdUseCase.find(id);

        if (isNull(customer)) {
            throw NotFoundException.of("Customer");
        }

        customerGateway.delete(id);
    }
}
