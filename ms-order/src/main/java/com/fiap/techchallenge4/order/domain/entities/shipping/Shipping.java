package com.fiap.techchallenge4.order.domain.entities.shipping;

import java.math.BigDecimal;

public interface Shipping {
    String getPostalCode();

    BigDecimal getPrice();

    Integer getDaysForDelivery();

    String getFormattedPrice();

    String getEstimate();

    String getTimeUnit();
}
