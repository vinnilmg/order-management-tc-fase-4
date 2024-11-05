package com.fiap.techchallenge4.order.infra.messaging.consumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {
    private Long id;
    private Long sku;
    private Integer quantity;
    private BigDecimal value;
    private BigDecimal total;
}
