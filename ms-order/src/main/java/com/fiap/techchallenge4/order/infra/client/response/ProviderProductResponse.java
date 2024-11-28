package com.fiap.techchallenge4.order.infra.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ProviderProductResponse(
        Long id,
        @JsonProperty("skuId")
        Long sku,
        String name,
        BigDecimal price,
        Integer stock
) {
}
