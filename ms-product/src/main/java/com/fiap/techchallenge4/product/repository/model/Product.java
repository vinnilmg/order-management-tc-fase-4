package com.fiap.techchallenge4.product.repository.model;

import com.fiap.techchallenge4.product.repository.entity.SkuTableData;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
