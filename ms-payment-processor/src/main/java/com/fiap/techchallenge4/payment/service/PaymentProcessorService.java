package com.fiap.techchallenge4.payment.service;

import com.fiap.techchallenge4.payment.controller.request.PaymentDataRequest;
import com.fiap.techchallenge4.payment.controller.response.PaymentProcessorResponse;

public interface PaymentProcessorService {
    PaymentProcessorResponse processPayment(PaymentDataRequest request);
}
