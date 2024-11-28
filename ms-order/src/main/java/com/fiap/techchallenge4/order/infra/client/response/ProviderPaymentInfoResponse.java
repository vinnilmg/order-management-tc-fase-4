package com.fiap.techchallenge4.order.infra.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProviderPaymentInfoResponse(
        String cardNumber,
        String expirationDate,
        @JsonProperty("cvvCode")
        String code
) {
}
