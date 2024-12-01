package com.fiap.techchallenge4.order.infra.messaging.consumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShippingDto {
    private String postalCode;
    private BigDecimal price;
    private Integer daysForDelivery;
}
