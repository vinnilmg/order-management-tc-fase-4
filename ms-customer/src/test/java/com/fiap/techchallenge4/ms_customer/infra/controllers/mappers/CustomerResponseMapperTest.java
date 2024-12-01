package com.fiap.techchallenge4.ms_customer.infra.controllers.mappers;

import com.fiap.techchallenge4.ms_customer.helper.fixture.CustomerFixture;
import com.fiap.techchallenge4.ms_customer.infra.controllers.response.CustomerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerResponseMapperTest {
    private CustomerResponseMapper customerResponseMapper;


    @BeforeEach
    void setup() {
        customerResponseMapper = new CustomerResponseMapperImpl();

    }

    @Test
    void shouldMapCustomerResponse() {
        final var customer = CustomerFixture.NEW_CUSTOMER();

        final var result = customerResponseMapper.toResponse(customer);

        assertThat(result)
                .isNotNull()
                .extracting(
                        CustomerResponse::id,
                        CustomerResponse::cpf,
                        CustomerResponse::name
                ).containsExactly(
                        customer.getId(),
                        customer.getCpf(),
                        customer.getName());
    }
}
