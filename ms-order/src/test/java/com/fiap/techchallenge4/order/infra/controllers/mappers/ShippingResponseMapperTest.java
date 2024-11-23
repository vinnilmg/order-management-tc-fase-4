package com.fiap.techchallenge4.order.infra.controllers.mappers;

import com.fiap.techchallenge4.order.helper.fixture.domain.ShippingDomainFixture;
import com.fiap.techchallenge4.order.infra.controllers.response.ShippingResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShippingResponseMapperTest {
    private ShippingResponseMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new ShippingResponseMapperImpl();
    }

    @Test
    void shouldReturnNullWhenShippingIsNull() {
        final var result = mapper.toResponse(null);
        assertThat(result)
                .isNull();
    }

    @Test
    void shouldMapShippingResponse() {
        final var shipping = ShippingDomainFixture.FULL();
        final var result = mapper.toResponse(shipping);
        assertThat(result)
                .isNotNull()
                .extracting(
                        ShippingResponse::postalCode,
                        ShippingResponse::price,
                        ShippingResponse::estimate
                ).containsExactly(
                        shipping.getPostalCode(),
                        shipping.getFormattedPrice(),
                        shipping.getEstimate()
                );
    }
}
