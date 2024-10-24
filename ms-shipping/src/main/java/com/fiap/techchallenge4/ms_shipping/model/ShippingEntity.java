package com.fiap.techchallenge4.ms_shipping.model;

import com.fiap.techchallenge4.ms_shipping.model.enums.RegionEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "shipping")
public class ShippingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private RegionEnum region;

    @NotNull
    private String cepStart;

    @NotNull
    private String cepEnd;

    @NotNull
    private String daysToDelivery;

    @NotNull
    private BigDecimal deliveryPrice;
}
