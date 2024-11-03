package com.fiap.techchallenge4.order.domain.entities.shipping;

import java.math.BigDecimal;

import static com.fiap.techchallenge4.order.utils.FormatterUtils.formatRealBrasileiro;
import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;

public class ShippingDomain implements Shipping {
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
        this.postalCode = requireNonNull(postalCode);
        this.price = requireNonNull(price);
        this.daysForDelivery = requireNonNull(daysForDelivery);
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
}
