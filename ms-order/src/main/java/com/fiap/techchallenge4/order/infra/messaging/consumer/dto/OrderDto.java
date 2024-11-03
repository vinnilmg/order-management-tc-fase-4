package com.fiap.techchallenge4.order.infra.messaging.consumer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
public class OrderDto {
    private Long id;
    private String cpf;
    private String status;
    private LocalDateTime creationDate;
    private List<ProductDto> products;
    private ShippingDto shipping;
    private BigDecimal total;
    private BigDecimal totalWithShipping;
}
