package com.fiap.techchallenge4.ms_shipping.repository.entities;

import com.fiap.techchallenge4.ms_shipping.repository.entities.enums.ShippingStatusEnum;
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
    private Long order_id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ShippingStatusEnum status;

    @NotNull
    private int latitude = 0;

    @NotNull
    private int longitude = 0;

    @Nullable
    @ManyToOne
    private CourierEntity courier;
}
