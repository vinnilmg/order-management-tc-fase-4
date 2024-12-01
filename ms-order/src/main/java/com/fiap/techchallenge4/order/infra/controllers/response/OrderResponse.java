package com.fiap.techchallenge4.order.infra.controllers.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public record OrderResponse(
        Long id,
        String cpf,
        String status,
        String creationDate,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String completionDate,
        List<ProductResponse> products,
        ShippingResponse shipping,
        String subtotal,
        String total
) {
}
