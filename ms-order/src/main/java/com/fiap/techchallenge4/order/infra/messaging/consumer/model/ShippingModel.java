package com.fiap.techchallenge4.order.infra.messaging.consumer.model;

import lombok.Data;

import java.math.BigDecimal;

import static java.util.Objects.isNull;

@Data
public class ShippingModel {
    private String postalCode;
    private BigDecimal price;
    private Integer daysForDelivery;

    public boolean validate() {
        var isValid = true;

        if (isNull(postalCode)) isValid = false;
        if (isNull(price)) isValid = false;
        if (isNull(daysForDelivery)) isValid = false;

        return isValid;
    }
}
