package com.fiap.techchallenge4.order.infra.client.response;

import java.math.BigDecimal;

public record ProviderShippingResponse(
        String postalCode,
        BigDecimal price,
        Integer daysForDelivery
) {
}
