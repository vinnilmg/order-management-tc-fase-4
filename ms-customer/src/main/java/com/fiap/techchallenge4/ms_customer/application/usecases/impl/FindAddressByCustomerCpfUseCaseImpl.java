package com.fiap.techchallenge4.ms_customer.application.usecases.impl;

import com.fiap.techchallenge4.ms_customer.application.gateways.AddressGateway;
import com.fiap.techchallenge4.ms_customer.application.usecases.FindAddressByCustomerCpfUseCase;
import com.fiap.techchallenge4.ms_customer.domain.entities.address.Address;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.NotFoundException;

import static java.util.Objects.isNull;

public class FindAddressByCustomerCpfUseCaseImpl implements FindAddressByCustomerCpfUseCase {
    private final AddressGateway addressGateway;

    public FindAddressByCustomerCpfUseCaseImpl(AddressGateway addressGateway) {
        this.addressGateway = addressGateway;
    }

    @Override
    public Address find(final String cpf) {
        if (isNull(cpf) || cpf.length() != 11) throw CustomValidationException.of("Customer CPF",
                "invalid");
        return addressGateway.findAddressByCustomerCpf(cpf)
                .orElseThrow(() -> NotFoundException.of("Customer Address"));
    }
}
