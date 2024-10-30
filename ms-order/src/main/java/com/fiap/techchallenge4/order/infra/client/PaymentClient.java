package com.fiap.techchallenge4.order.infra.client;

import com.fiap.techchallenge4.order.infra.client.request.ProcessPaymentRequest;
import com.fiap.techchallenge4.order.infra.client.response.ProviderPaymentProcessedResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "payment", url = "${ms.payment.host}/api/payment")
public interface PaymentClient {

    @PostMapping(value = "/processor", produces = "application/json")
    ProviderPaymentProcessedResponse processPayment(@RequestBody ProcessPaymentRequest request);
}
