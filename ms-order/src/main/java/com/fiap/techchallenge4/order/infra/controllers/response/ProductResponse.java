package com.fiap.techchallenge4.order.infra.controllers.response;

public record ProductResponse(
        Long id,
        Long sku,
        Integer quantity,
        String value
) {
}
