package com.fiap.techchallenge4.ms_shipping.model;

import com.fiap.techchallenge4.ms_shipping.model.enums.AvaialableCourierEnum;
import com.fiap.techchallenge4.ms_shipping.model.enums.RegionEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "courier")
public class CourierEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AvaialableCourierEnum status;

    @NotNull
    private String phone;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RegionEnum region;
}
