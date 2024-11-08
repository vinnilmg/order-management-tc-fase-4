package com.fiap.techchallenge4.ms_customer.application.usecases;

import com.fiap.techchallenge4.ms_customer.domain.entities.customer.Customer;

@FunctionalInterface
public interface FindCustomerByIdUseCase {
    Customer find(Long id);
}
