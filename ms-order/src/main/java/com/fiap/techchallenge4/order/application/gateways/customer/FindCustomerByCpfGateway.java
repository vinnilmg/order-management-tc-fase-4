package com.fiap.techchallenge4.order.application.gateways.customer;

import com.fiap.techchallenge4.order.domain.entities.customer.Customer;

import java.util.Optional;

@FunctionalInterface
public interface FindCustomerByCpfGateway {
    Optional<Customer> find(String cpf);
}
