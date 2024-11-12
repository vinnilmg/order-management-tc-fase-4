package com.fiap.techchallenge4.order.infra.client.mappers;

import com.fiap.techchallenge4.order.domain.entities.product.Product;
import com.fiap.techchallenge4.order.helper.fixture.response.ProviderProductResponseFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProviderProductResponseMapperTest {
    private ProviderProductResponseMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new ProviderProductResponseMapperImpl();
    }

    @Test
    void shouldReturnNullWhenResponseIsNull() {
        final var result = mapper.toProductDomain(null);
        assertThat(result)
                .isNull();
    }

    @Test
    void shouldMapProductDomain() {
        final var response = ProviderProductResponseFixture.FULL();
        final var result = mapper.toProductDomain(response);
        assertThat(result)
                .isNotNull()
                .extracting(
                        Product::getId,
                        Product::getSku,
                        Product::getQuantity,
                        Product::getValue
                ).containsExactly(
                        response.id(),
                        response.sku(),
                        response.stock(),
                        response.price()
                );
    }
}
