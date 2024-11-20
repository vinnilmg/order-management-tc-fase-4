package com.fiap.techchallenge4.ms_customer.infra.controllers.mappers;

import com.fiap.techchallenge4.ms_customer.domain.entities.customer.Customer;
import com.fiap.techchallenge4.ms_customer.helper.fixture.request.CustomerRequestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerRequestMapperTest {
    private CustomerRequestMapper customerRequestMapper;

    @BeforeEach
    void setup() {
        customerRequestMapper = new CustomerRequestMapperImpl();
    }

    @Test
    void shouldMapCustomer() {
        final var customerRequest = CustomerRequestFixture.FULL();

        final var result = customerRequestMapper.toCustomer(customerRequest);

        assertThat(result)
                .isNotNull()
                .extracting(
                        Customer::getCpf,
                        Customer::getName)
                .containsExactly(
                        customerRequest.cpf(),
                        customerRequest.name());
    }
}
