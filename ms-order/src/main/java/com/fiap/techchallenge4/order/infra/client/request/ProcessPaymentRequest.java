package com.fiap.techchallenge4.order.infra.client.request;

public record ProcessPaymentRequest(
        String cardNumber,
        String expirationDate,
        String code
) {
    public static ProcessPaymentRequest of(final String cardNumber, final String expirationDate, final String code) {
        return new ProcessPaymentRequest(cardNumber, expirationDate, code);
    }
}
