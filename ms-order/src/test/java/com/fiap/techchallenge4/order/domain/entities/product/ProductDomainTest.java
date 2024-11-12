package com.fiap.techchallenge4.order.domain.entities.product;

import com.fiap.techchallenge4.order.domain.exceptions.CustomValidationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.fiap.techchallenge4.order.utils.FormatterUtils.formatRealBrasileiro;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductDomainTest {

    @Test
    void givenASkuNullWhenConstructThenThrowCustomValidationException() {
        assertThatThrownBy(() -> ProductDomain.of(null, null, null))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Product Sku cannot be null, zero or negative");
    }

    @Test
    void givenAQuantityNullWhenConstructThenThrowCustomValidationException() {
        assertThatThrownBy(() -> ProductDomain.of(1L, null, null))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Product Quantity cannot be null, zero or negative");
    }

    @Test
    void givenAValueNullWhenConstructThenThrowCustomValidationException() {
        assertThatThrownBy(() -> ProductDomain.of(1L, 1, null))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Product Value cannot be null, zero or negative");
    }

    @Test
    void givenAIdNullWhenConstructThenThrowNullPointerException() {
        assertThatThrownBy(() -> ProductDomain.of(null, null, null, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Product Id cannot be null");
    }

    @Test
    void shouldConstructProductDomain() {
        final var id = 1L;
        final var sku = 3000L;
        final var quantity = 1;
        final var value = new BigDecimal(5);
        final var result = ProductDomain.of(id, sku, quantity, value);
        assertThat(result)
                .isNotNull()
                .extracting(
                        Product::getId,
                        Product::getSku,
                        Product::getQuantity,
                        Product::getValue,
                        Product::getFormattedValue,
                        Product::getTotal
                ).containsExactly(
                        id,
                        sku,
                        quantity,
                        value,
                        formatRealBrasileiro(value),
                        value.multiply(BigDecimal.valueOf(quantity))
                                .setScale(2, RoundingMode.HALF_UP)
                );
    }
}
