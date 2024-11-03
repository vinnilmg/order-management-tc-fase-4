package com.fiap.techchallenge4.order.domain.entities.product;

import java.math.BigDecimal;

public interface Product {
    Long getId();

    Long getSku();

    Integer getQuantity();

    BigDecimal getValue();

    String getFormattedValue();

    BigDecimal getTotal();
}
