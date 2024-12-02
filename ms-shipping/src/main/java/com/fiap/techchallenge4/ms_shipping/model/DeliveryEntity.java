package com.fiap.techchallenge4.ms_shipping.model;

import com.fiap.techchallenge4.ms_shipping.model.enums.DeliveryStatusEnum;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "delivery")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private Long orderId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DeliveryStatusEnum status;

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
