package com.fiap.techchallenge4.ms_shipping.dto;

import com.fiap.techchallenge4.ms_shipping.model.enums.DeliveryStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDto {

    private Long orderId;
    private DeliveryStatusEnum status;
    private int latitude;
    private int longitude;
}
