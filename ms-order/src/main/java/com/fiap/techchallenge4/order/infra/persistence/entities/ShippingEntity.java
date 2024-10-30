package com.fiap.techchallenge4.order.infra.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "shippings")
public class ShippingEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String postalCode;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer estimate;

    private String timeUnit;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;
}
