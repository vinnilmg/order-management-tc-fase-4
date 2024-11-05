package com.fiap.techchallenge4.product.core.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class ProductData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long skuId;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stock;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime createdDateTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime lastDateTimeModified;

    @PrePersist
    public void onCreatedDate() {
        this.createdDateTime = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdateLastTimeModified() {
        this.lastDateTimeModified = LocalDateTime.now();
    }

}
