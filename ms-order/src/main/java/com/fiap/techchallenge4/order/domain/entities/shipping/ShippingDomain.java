package com.fiap.techchallenge4.order.domain.entities.shipping;

import com.fiap.techchallenge4.order.domain.exceptions.CustomValidationException;

import java.math.BigDecimal;

import static com.fiap.techchallenge4.order.utils.FormatterUtils.formatRealBrasileiro;
import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;

public class ShippingDomain implements Shipping {
    private static final String DEFAULT_TIME_UNIT = "DAY";

    private final String postalCode;
    private final BigDecimal price;
    private final Integer daysForDelivery;

    public static Shipping of(
            final String postalCode,
            final BigDecimal price,
            final Integer daysForDelivery
    ) {
        return new ShippingDomain(postalCode, price, daysForDelivery);
    }

    public ShippingDomain(
            final String postalCode,
            final BigDecimal price,
            final Integer daysForDelivery
    ) {
        requireNonNull(postalCode, "Postal Code cannot be null");
        if (postalCode.length() != 8) {
            throw CustomValidationException.of("Postal Code", "must contains 8 positions");
        }

        requireNonNull(price, "Price cannot be null");
        if (price.compareTo(BigDecimal.ZERO) < 1) {
            throw CustomValidationException.of("Price", "must be positive");
        }

        requireNonNull(daysForDelivery, "Days For Delivery cannot be null");
        if (daysForDelivery < 1) {
            throw CustomValidationException.of("Days For Delivery", "must be at least 1");
        }

        this.postalCode = postalCode;
        this.price = price;
        this.daysForDelivery = daysForDelivery;
    }

    @Override
    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public Integer getDaysForDelivery() {
        return daysForDelivery;
    }

    @Override
    public String getFormattedPrice() {
        return formatRealBrasileiro(price);
    }

    @Override
    public String getEstimate() {
        return format("{0} days", daysForDelivery);
    }

    @Override
    public String getTimeUnit() {
        return DEFAULT_TIME_UNIT;
    }
}
