package com.fiap.techchallenge4.order.infra.messaging.consumer.model;

import lombok.Data;

import java.math.BigDecimal;

import static java.util.Objects.isNull;

@Data
public class ProductModel {
    private Long id;
    private Long sku;
    private Integer quantity;
    private BigDecimal value;
    private BigDecimal total;

    public boolean validate() {
        var isValid = true;

        if (isNull(id)) isValid = false;
        if (isNull(sku)) isValid = false;
        if (isNull(quantity) || quantity < 1) isValid = false;
        if (isNull(value) || value.compareTo(BigDecimal.ZERO) < 1) isValid = false;
        if (isNull(total) || total.compareTo(BigDecimal.ZERO) < 1) isValid = false;

        return isValid;
    }
}
