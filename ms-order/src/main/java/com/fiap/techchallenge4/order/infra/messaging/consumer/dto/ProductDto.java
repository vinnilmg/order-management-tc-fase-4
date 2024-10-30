package com.fiap.techchallenge4.order.infra.messaging.consumer.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private Long id;
    private Long sku;
    private Integer quantity;
    private BigDecimal value;
    private BigDecimal total;
}
