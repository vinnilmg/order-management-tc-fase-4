package com.fiap.techchallenge4.ms_customer.application.usecases;

import com.fiap.techchallenge4.ms_customer.domain.entities.customer.Customer;
import java.util.List;

@FunctionalInterface
public interface FindAllCustomersUseCase {
    List<Customer> find();
}
