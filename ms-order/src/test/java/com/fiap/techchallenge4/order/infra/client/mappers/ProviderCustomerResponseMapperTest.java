package com.fiap.techchallenge4.order.infra.client.mappers;

import com.fiap.techchallenge4.order.domain.entities.customer.Customer;
import com.fiap.techchallenge4.order.helper.fixture.response.ProviderCustomerResponseFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProviderCustomerResponseMapperTest {
    private ProviderCustomerResponseMapper mapper;

    @BeforeEach
    void init() {
        mapper = new ProviderCustomerResponseMapperImpl();
    }

    @Test
    void shouldReturnNullWhenResponseIsNull() {
        final var result = mapper.toCustomerDomain(null);
        assertThat(result)
                .isNull();
    }

    @Test
    void shouldMapCustomerDomain() {
        final var response = ProviderCustomerResponseFixture.FULL();
        final var result = mapper.toCustomerDomain(response);
        assertThat(result)
                .isNotNull()
                .extracting(
                        Customer::getId,
                        Customer::getCpf,
                        Customer::getName,
                        Customer::getBirthDate
                ).containsExactly(
                        response.id(),
                        response.cpf(),
                        response.name(),
                        response.birthDate()
                );
    }
}
