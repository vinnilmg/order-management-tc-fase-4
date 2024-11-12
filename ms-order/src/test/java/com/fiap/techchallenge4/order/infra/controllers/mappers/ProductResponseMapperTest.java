package com.fiap.techchallenge4.order.infra.controllers.mappers;

import com.fiap.techchallenge4.order.helper.fixture.domain.ProductDomainFixture;
import com.fiap.techchallenge4.order.infra.controllers.response.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductResponseMapperTest {
    private ProductResponseMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new ProductResponseMapperImpl();
    }

    @Test
    void shouldReturnNullWhenOrdersIsNull() {
        final var result = mapper.toResponses(null);
        assertThat(result)
                .isNull();
    }

    @Test
    void shouldReturnNullWhenOrderIsNull() {
        final var result = mapper.toResponse(null);
        assertThat(result)
                .isNull();
    }

    @Test
    void shouldMapProductsResponse() {
        final var products = List.of(ProductDomainFixture.FULL());
        final var result = mapper.toResponses(products);
        assertThat(result)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    void shouldMapProductResponse() {
        final var product = ProductDomainFixture.FULL();
        final var result = mapper.toResponse(product);
        assertThat(result)
                .isNotNull()
                .extracting(
                        ProductResponse::id,
                        ProductResponse::sku,
                        ProductResponse::quantity,
                        ProductResponse::value
                ).containsExactly(
                        product.getId(),
                        product.getSku(),
                        product.getQuantity(),
                        product.getFormattedValue()
                );
    }
}
