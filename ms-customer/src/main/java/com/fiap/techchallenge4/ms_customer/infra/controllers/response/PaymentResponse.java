package com.fiap.techchallenge4.ms_customer.infra.controllers.response;

import java.time.LocalDate;

public record PaymentResponse(
        Long id,
        String cardNumber,
        LocalDate expirationDate,
        String cvvCode)
{
}
