package com.fiap.techchallenge4.ms_payment_processor.controller.response;

public record PaymentProcessorResponse(boolean approved) {
    public static PaymentProcessorResponse of(final boolean approved) {
        return new PaymentProcessorResponse(approved);
    }
}
