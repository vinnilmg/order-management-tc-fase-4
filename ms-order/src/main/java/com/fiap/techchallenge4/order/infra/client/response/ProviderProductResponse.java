package com.fiap.techchallenge4.order.infra.client.response;

import java.math.BigDecimal;

public record ProviderProductResponse(
        Long id,
        Long sku,
        String name,
        BigDecimal price,
        Integer stock
) {
}
