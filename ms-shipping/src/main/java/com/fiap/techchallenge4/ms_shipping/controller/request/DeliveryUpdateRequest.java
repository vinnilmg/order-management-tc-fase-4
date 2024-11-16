package com.fiap.techchallenge4.ms_shipping.controller.request;

import com.fiap.techchallenge4.ms_shipping.model.CourierEntity;
import com.fiap.techchallenge4.ms_shipping.model.enums.ShippingStatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryUpdateRequest {

    private ShippingStatusEnum status;
    private Long id;

    public DeliveryUpdateRequest(ShippingStatusEnum status, Long id) {
        this.status = status;
        this.id = id;
    }
}
