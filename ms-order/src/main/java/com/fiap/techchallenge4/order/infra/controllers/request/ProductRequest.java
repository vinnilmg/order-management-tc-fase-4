package com.fiap.techchallenge4.order.infra.controllers.request;

import java.math.BigDecimal;

public record ProductRequest(
        Long sku,
        Integer quantity,
        BigDecimal unitaryValue
) {
}
