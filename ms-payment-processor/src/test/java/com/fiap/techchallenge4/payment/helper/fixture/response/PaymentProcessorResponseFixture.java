package com.fiap.techchallenge4.payment.helper.fixture.response;

import com.fiap.techchallenge4.payment.controller.response.PaymentProcessorResponse;

public class PaymentProcessorResponseFixture {

    public static PaymentProcessorResponse APPROVED() {
        return PaymentProcessorResponse.of(true);
    }
}
