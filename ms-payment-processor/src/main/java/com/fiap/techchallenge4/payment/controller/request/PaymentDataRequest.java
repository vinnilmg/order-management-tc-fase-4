package com.fiap.techchallenge4.ms_payment_processor.controller.request;

public record PaymentDataRequest(
        String cardNumber,
        String expirationDate,
        String code
) {
}
