package com.fiap.techchallenge4.ms_customer.application.usecases;

import com.fiap.techchallenge4.ms_customer.domain.entities.address.Address;

@FunctionalInterface
public interface FindAddressByCustomerCpfUseCase {
    Address find(String cpf);
}
