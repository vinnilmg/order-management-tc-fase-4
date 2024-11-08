package com.fiap.techchallenge4.ms_customer.application.usecases;

import com.fiap.techchallenge4.ms_customer.domain.entities.customer.Customer;

import java.util.Optional;

@FunctionalInterface
public interface FindCustomerByCpfUseCase {
    Customer find(String cpf);
}
