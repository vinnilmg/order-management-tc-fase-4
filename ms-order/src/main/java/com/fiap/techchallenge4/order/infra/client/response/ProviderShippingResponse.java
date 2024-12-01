package com.fiap.techchallenge4.order.infra.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record ProviderShippingResponse(
        String postalCode,
        @JsonProperty("deliveryPrice")
        BigDecimal price,
        @JsonProperty("daysToDelivery")
        Integer daysForDelivery
) {
}
