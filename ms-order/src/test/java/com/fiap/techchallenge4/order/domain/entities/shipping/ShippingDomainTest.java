package com.fiap.techchallenge4.order.domain.entities.shipping;

import com.fiap.techchallenge4.order.domain.exceptions.CustomValidationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.fiap.techchallenge4.order.helper.constants.AddressConstants.POSTAL_CODE;
import static com.fiap.techchallenge4.order.utils.FormatterUtils.formatRealBrasileiro;
import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ShippingDomainTest {

    @Test
    void givenAPostalCodeNullWhenConstructThenThrowNullPointerException() {
        assertThatThrownBy(() -> ShippingDomain.of(null, null, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Postal Code cannot be null");
    }

    @Test
    void givenAnInvalidPostalCodeWhenConstructThenCustomValidationException() {
        assertThatThrownBy(() -> ShippingDomain.of("1234", null, null))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Postal Code must contains 8 positions");
    }

    @Test
    void givenAPriceNullWhenConstructThenThrowNullPointerException() {
        assertThatThrownBy(() -> ShippingDomain.of(POSTAL_CODE, null, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Price cannot be null");
    }

    @Test
    void givenAZeroPriceWhenConstructThenThrowCustomValidationException() {
        assertThatThrownBy(() -> ShippingDomain.of(POSTAL_CODE, BigDecimal.ZERO, null))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Price must be positive");
    }

    @Test
    void givenADaysForDeliveryNullWhenConstructThenThrowNullPointerException() {
        assertThatThrownBy(() -> ShippingDomain.of(POSTAL_CODE, new BigDecimal("1"), null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Days For Delivery cannot be null");
    }

    @Test
    void givenAZeroDaysForDeliveryWhenConstructThenThrowCustomValidationException() {
        assertThatThrownBy(() -> ShippingDomain.of(POSTAL_CODE, new BigDecimal("1"), 0))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Days For Delivery must be at least 1");
    }

    @Test
    void shouldConstructShippingDomain() {
        final var postalCode = POSTAL_CODE;
        final var price = new BigDecimal(5);
        final var daysForDelivery = 5;
        final var result = ShippingDomain.of(postalCode, price, daysForDelivery);
        assertThat(result)
                .isNotNull()
                .extracting(
                        Shipping::getPostalCode,
                        Shipping::getPrice,
                        Shipping::getDaysForDelivery,
                        Shipping::getFormattedPrice,
                        Shipping::getEstimate,
                        Shipping::getTimeUnit
                ).containsExactly(
                        postalCode,
                        price,
                        daysForDelivery,
                        formatRealBrasileiro(price),
                        format("{0} days", daysForDelivery),
                        "DAY"
                );
    }
}
