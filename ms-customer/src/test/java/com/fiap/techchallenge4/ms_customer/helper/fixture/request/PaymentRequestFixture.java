package com.fiap.techchallenge4.ms_customer.helper.fixture.request;

import com.fiap.techchallenge4.ms_customer.infra.controllers.request.PaymentRequest;

import java.time.LocalDate;

public class PaymentRequestFixture {
    public static PaymentRequest FULL() {
        return new PaymentRequest(
                "5329694917211404",
                LocalDate.of(2026, 10, 9),
                "361",
                1L
        );
    }
}
