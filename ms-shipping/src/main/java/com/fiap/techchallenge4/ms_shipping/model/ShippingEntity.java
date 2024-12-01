package com.fiap.techchallenge4.ms_shipping.model;

import com.fiap.techchallenge4.ms_shipping.model.enums.RegionEnum;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "shipping")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShippingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private RegionEnum region;

    @NotNull
    @Size(min= 8, max = 8, message = "CEP must have 8 characters")
    private String cepStart;

    @NotNull
    @Size(min= 8, max = 8, message = "CEP must have 8 characters")
    private String cepEnd;

    @NotNull
    private String daysToDelivery;

    @NotNull
    private BigDecimal deliveryPrice;
}
