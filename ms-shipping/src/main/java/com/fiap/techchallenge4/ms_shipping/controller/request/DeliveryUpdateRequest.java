package com.fiap.techchallenge4.ms_shipping.controller.request;

import com.fiap.techchallenge4.ms_shipping.model.enums.DeliveryStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryUpdateRequest {

    private DeliveryStatusEnum status;
    private Long id;

    public DeliveryUpdateRequest(DeliveryStatusEnum status, Long id) {
        this.status = status;
        this.id = id;
    }
}
