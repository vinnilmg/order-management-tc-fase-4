package com.fiap.techchallenge4.product.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class Product {


    private Long id;

    private SkuTable skuTable;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stock;

    private LocalDateTime createdDateTime;
}
