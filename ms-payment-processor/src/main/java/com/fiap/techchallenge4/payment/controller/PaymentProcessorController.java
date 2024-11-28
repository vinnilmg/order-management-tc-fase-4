package com.fiap.techchallenge4.ms_payment_processor.controller;

import com.fiap.techchallenge4.ms_payment_processor.controller.request.PaymentDataRequest;
import com.fiap.techchallenge4.ms_payment_processor.controller.response.PaymentProcessorResponse;
import com.fiap.techchallenge4.ms_payment_processor.service.PaymentProcessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment/processor")
public class PaymentProcessorController {
    private final PaymentProcessorService paymentProcessorService;

    public PaymentProcessorController(PaymentProcessorService paymentProcessorService) {
        this.paymentProcessorService = paymentProcessorService;
    }

    @PostMapping
    public ResponseEntity<PaymentProcessorResponse> process(@RequestBody final PaymentDataRequest request) {
        return ResponseEntity.ok(paymentProcessorService.processPayment(request));
    }
}
