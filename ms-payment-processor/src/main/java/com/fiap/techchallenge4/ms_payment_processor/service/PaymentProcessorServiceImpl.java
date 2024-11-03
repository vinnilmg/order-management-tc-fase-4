package com.fiap.techchallenge4.ms_payment_processor.service;

import com.fiap.techchallenge4.ms_payment_processor.controller.request.PaymentDataRequest;
import com.fiap.techchallenge4.ms_payment_processor.controller.response.PaymentProcessorResponse;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class PaymentProcessorServiceImpl implements PaymentProcessorService {
    @Override
    public PaymentProcessorResponse processPayment(final PaymentDataRequest request) {
        requireNonNull(request, "Request cannot be null");
        requireNonNull(request.cardNumber(), "Card Number cannot be null");
        requireNonNull(request.expirationDate(), "Expiration Date cannot be null");
        requireNonNull(request.code(), "Code cannot be null");

        final var approved = !request.code().endsWith("9");
        return PaymentProcessorResponse.of(approved);
    }
}
