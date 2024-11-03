package com.fiap.techchallenge4.order.infra.client.response;

public record ProviderPaymentInfoResponse(
        String cardNumber,
        String expirationDate,
        String code
) {
}
