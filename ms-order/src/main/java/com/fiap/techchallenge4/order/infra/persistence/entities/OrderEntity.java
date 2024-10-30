package com.fiap.techchallenge4.order.infra.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class OrderEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String cpf;

    @NotNull
    private String status;

    @NotNull
    private BigDecimal total;

    @NotNull
    private LocalDateTime orderCreationDate;

    private LocalDateTime orderCompletionDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.LAZY)
    private List<ProductEntity> products;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "order")
    private ShippingEntity shipping;
}
