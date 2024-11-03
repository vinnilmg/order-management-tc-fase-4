package com.fiap.techchallenge4.order.infra.messaging.consumer.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShippingDto {
    private String postalCode;
    private BigDecimal price;
    private Integer daysForDelivery;
}
