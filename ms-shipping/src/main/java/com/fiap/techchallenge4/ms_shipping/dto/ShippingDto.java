package com.fiap.techchallenge4.ms_shipping.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShippingDto {

    private String daysToDelivery;
    private BigDecimal deliveryPrice;
    private String postalCode;
}
