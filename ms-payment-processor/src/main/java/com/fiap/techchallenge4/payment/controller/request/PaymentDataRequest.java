package com.fiap.techchallenge4.payment.controller.request;

public record PaymentDataRequest(
        String cardNumber,
        String expirationDate,
        String code
) {
}
