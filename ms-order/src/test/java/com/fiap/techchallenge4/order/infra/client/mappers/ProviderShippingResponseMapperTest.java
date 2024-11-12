package com.fiap.techchallenge4.order.infra.client.mappers;

import com.fiap.techchallenge4.order.domain.entities.shipping.Shipping;
import com.fiap.techchallenge4.order.helper.fixture.response.ProviderShippingResponseFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProviderShippingResponseMapperTest {
    private ProviderShippingResponseMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new ProviderShippingResponseMapperImpl();
    }

    @Test
    void shouldReturnNullWhenResponseIsNull() {
        final var result = mapper.toShippingDomain(null);
        assertThat(result)
                .isNull();
    }

    @Test
    void shouldMapShippingDomain() {
        final var response = ProviderShippingResponseFixture.FULL();
        final var result = mapper.toShippingDomain(response);
        assertThat(result)
                .isNotNull()
                .extracting(
                        Shipping::getPostalCode,
                        Shipping::getDaysForDelivery,
                        Shipping::getPrice
                ).containsExactly(
                        response.postalCode(),
                        response.daysForDelivery(),
                        response.price()
                );
    }
}
