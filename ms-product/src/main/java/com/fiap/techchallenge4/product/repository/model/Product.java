package com.fiap.techchallenge4.product.repository.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Product {

    private Long id;

    private SkuTable skuTable;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer estoque;

    private LocalDateTime createdDateTime;
}
