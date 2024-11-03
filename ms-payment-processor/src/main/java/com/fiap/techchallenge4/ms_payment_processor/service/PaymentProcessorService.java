package com.fiap.techchallenge4.ms_payment_processor.service;

import com.fiap.techchallenge4.ms_payment_processor.controller.request.PaymentDataRequest;
import com.fiap.techchallenge4.ms_payment_processor.controller.response.PaymentProcessorResponse;

public interface PaymentProcessorService {
    PaymentProcessorResponse processPayment(PaymentDataRequest request);
}
