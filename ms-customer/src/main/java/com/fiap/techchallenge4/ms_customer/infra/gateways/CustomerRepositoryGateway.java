package com.fiap.techchallenge4.ms_customer.infra.gateways;

import com.fiap.techchallenge4.ms_customer.application.gateways.CustomerGateway;
import com.fiap.techchallenge4.ms_customer.domain.entities.customer.Customer;
import com.fiap.techchallenge4.ms_customer.infra.persistence.mappers.CustomerEntityMapper;
import com.fiap.techchallenge4.ms_customer.infra.persistence.repositories.CustomerRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerRepositoryGateway implements CustomerGateway {
    private final CustomerRepository customerRepository;
    private final CustomerEntityMapper customerEntityMapper;

    public CustomerRepositoryGateway(CustomerRepository customerRepository,
                                     CustomerEntityMapper customerEntityMapper) {
        this.customerRepository = customerRepository;
        this.customerEntityMapper = customerEntityMapper;
    }

    @Override
    public Customer create(final Customer customer) {
        final var entity = customerEntityMapper.toEntity(customer);
        final var entitySaved = customerRepository.save(entity);
        return customerEntityMapper.toDomain(entitySaved);
    }

    @Override
    public List<Customer> findAllCustomers() {
        final var entities = customerRepository.findAll();
        return customerEntityMapper.toDomains(entities);
    }

    @Override
    public Optional<Customer> findCustomerById(final Long id) {
        return customerRepository.findById(id)
                .map(customerEntityMapper::toDomain);
    }

    @Override
    public Optional<Customer> findCustomerByCpf(final String cpf) {
        return customerRepository.findByCpf(cpf)
                .map(customerEntityMapper::toDomain);
    }

    @Override
    public void update(final Customer customer) {
        final var entity = customerEntityMapper.toEntity(customer);
        customerRepository.save(entity);
    }

    @Override
    public void delete(final Long id) {
        customerRepository.deleteById(id);
    }
}
