package com.fiap.techchallenge4.ms_customer.infra.gateways;

import com.fiap.techchallenge4.ms_customer.helper.fixture.CustomerFixture;
import com.fiap.techchallenge4.ms_customer.helper.fixture.entity.CustomerEntityFixture;
import com.fiap.techchallenge4.ms_customer.infra.persistence.mappers.CustomerEntityMapper;
import com.fiap.techchallenge4.ms_customer.infra.persistence.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.fiap.techchallenge4.ms_customer.helper.constants.CustomerConstants.DEFAULT_CPF;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class CustomerRepositoryGatewayTest {
    @InjectMocks
    private CustomerRepositoryGateway customerRepositoryGateway;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerEntityMapper customerEntityMapper;

    @Test
    void shouldFindAllCustomers() {
        final var customers = List.of(CustomerEntityFixture.CREATED());
        final var expectedCustomers = CustomerFixture.CUSTOMERS();

        when(customerRepository.findAll())
                .thenReturn(customers);

        when(customerEntityMapper.toDomains(customers))
                .thenReturn(expectedCustomers);

        final var result = customerRepositoryGateway.findAllCustomers();

        assertThat(result)
                .isNotEmpty()
                .hasSize(customers.size())
                .isEqualTo(expectedCustomers);

        verify(customerRepository).findAll();
        verify(customerEntityMapper).toDomains(customers);
    }

    @Test
    void shouldFindCustomerById() {
        final var customerId = 1L;
        final var customerEntity = CustomerEntityFixture.CREATED();
        final var expectedCustomer = CustomerFixture.NEW_CUSTOMER();

        when(customerRepository.findById(customerId))
                .thenReturn(Optional.of(customerEntity));

        when(customerEntityMapper.toDomain(customerEntity))
                .thenReturn(expectedCustomer);

        final var result = customerRepositoryGateway.findCustomerById(customerId);

        assertThat(result)
                .isNotNull()
                .isPresent()
                .hasValue(expectedCustomer);

        verify(customerRepository).findById(customerId);
        verify(customerEntityMapper).toDomain(customerEntity);
    }

    @Test
    void shouldFindCustomerByCpf() {
        final var cpf = DEFAULT_CPF;
        final var customerEntity = CustomerEntityFixture.CREATED();
        final var expectedCustomer = CustomerFixture.NEW_CUSTOMER();

        when(customerRepository.findByCpf(cpf))
                .thenReturn(Optional.of(customerEntity));

        when(customerEntityMapper.toDomain(customerEntity))
                .thenReturn(expectedCustomer);

        final var result = customerRepositoryGateway.findCustomerByCpf(cpf);

        assertThat(result)
                .isNotNull()
                .isPresent()
                .hasValue(expectedCustomer);

        verify(customerRepository).findByCpf(cpf);
        verify(customerEntityMapper).toDomain(customerEntity);

    }

    @Test
    void shouldCreateCustomer() {
        final var customer = CustomerFixture.NEW_CUSTOMER();
        final var customerEntity = CustomerEntityFixture.CREATED();

        when(customerEntityMapper.toEntity(customer))
                .thenReturn(customerEntity);

        when(customerRepository.save(customerEntity))
                .thenReturn(customerEntity);

        when(customerEntityMapper.toDomain(customerEntity))
                .thenReturn(customer);

        final var result = customerRepositoryGateway.create(customer);

        assertThat(result)
                .isNotNull()
                .isEqualTo(customer);

        verify(customerEntityMapper).toEntity(customer);
        verify(customerRepository).save(customerEntity);
        verify(customerEntityMapper).toDomain(customerEntity);
    }

    @Test
    void shouldUpdateCustomer() {
        final var customer = CustomerFixture.NEW_CUSTOMER();
        final var customerEntity = CustomerEntityFixture.CREATED();

        when(customerEntityMapper.toEntity(customer))
                .thenReturn(customerEntity);

        customerRepositoryGateway.update(customer);

        verify(customerEntityMapper).toEntity(customer);
        verify(customerRepository).save(customerEntity);
    }

    @Test
    void shouldDeleteCustomer() {
        final var customerId = 1L;

        customerRepositoryGateway.delete(customerId);

        verify(customerRepository).deleteById(customerId);

    }

}
