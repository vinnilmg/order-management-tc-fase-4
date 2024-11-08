package com.fiap.techchallenge4.ms_shipping.model;

import com.fiap.techchallenge4.ms_shipping.model.enums.ShippingStatusEnum;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "delivery")
public class DeliveryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long orderId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ShippingStatusEnum status;

    @NotNull
    @Column(columnDefinition = "int default 0")
    private int latitude = 0;

    @NotNull
    @Column(columnDefinition = "int default 0")
    private int longitude = 0;

    @Nullable
    @ManyToOne
    private CourierEntity courier;

    @ManyToOne
    private ShippingEntity region;
}