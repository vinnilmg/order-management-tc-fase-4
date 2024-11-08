package com.fiap.techchallenge4.ms_shipping.dto;

import com.fiap.techchallenge4.ms_shipping.model.enums.ShippingStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDto {

    private Long order_id;
    private ShippingStatusEnum status;
    private int latitude;
    private int longitude;
}
