package com.fiap.techchallenge4.ms_customer.application.gateways;

import com.fiap.techchallenge4.ms_customer.domain.entities.customer.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerGateway {
    Customer create(Customer customer);

    List<Customer> getAllOrders();

    Optional<Customer> findCustomerById(Long id);

    Optional<Customer> findCustomerByCpf(String cpf);

    void update(Customer customer);

    void delete(Long id);
}
