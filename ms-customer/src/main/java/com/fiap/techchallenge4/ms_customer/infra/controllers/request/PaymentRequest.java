package com.fiap.techchallenge4.ms_customer.infra.controllers.request;

import java.time.LocalDate;

public record PaymentRequest(
        String cardNumber,
        LocalDate expirationDate,
        String cvvCode,
        Long customerId
) {
}
