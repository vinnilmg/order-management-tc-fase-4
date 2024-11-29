package com.fiap.techchallenge4.ms_shipping.controller.request;

import com.fiap.techchallenge4.ms_shipping.model.enums.AvaialableCourierEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourierUpdateRequest {

    private AvaialableCourierEnum status;

    public CourierUpdateRequest() {}

    public CourierUpdateRequest(AvaialableCourierEnum status) {
        this.status = status;
    }
}
