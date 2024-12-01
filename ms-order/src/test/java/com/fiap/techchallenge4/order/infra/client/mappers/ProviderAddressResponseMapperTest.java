package com.fiap.techchallenge4.order.infra.client.mappers;

import com.fiap.techchallenge4.order.domain.entities.customer.address.Address;
import com.fiap.techchallenge4.order.helper.fixture.response.ProviderAddressResponseFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProviderAddressResponseMapperTest {
    private ProviderAddressResponseMapper mapper;

    @BeforeEach
    void init() {
        mapper = new ProviderAddressResponseMapperImpl();
    }

    @Test
    void shouldReturnNullWhenResponseIsNull() {
        final var result = mapper.toAddressDomain(null);
        assertThat(result)
                .isNull();
    }

    @Test
    void shouldMapAddressDomain() {
        final var response = ProviderAddressResponseFixture.FULL();
        final var result = mapper.toAddressDomain(response);
        assertThat(result)
                .isNotNull()
                .extracting(
                        Address::getId,
                        Address::getPostalCode,
                        Address::getCity,
                        Address::getNeighborhood,
                        Address::getState,
                        Address::getStreet,
                        Address::getNumber,
                        Address::getComplement
                ).containsExactly(
                        response.id(),
                        response.postalCode(),
                        response.city(),
                        response.neighborhood(),
                        response.state(),
                        response.street(),
                        response.number(),
                        response.complement()
                );
    }
}
