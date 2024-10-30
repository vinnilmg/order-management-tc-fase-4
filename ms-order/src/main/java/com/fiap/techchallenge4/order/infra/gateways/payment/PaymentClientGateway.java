package com.fiap.techchallenge4.order.infra.gateways.payment;

import com.fiap.techchallenge4.order.application.gateways.payment.ProcessPaymentGateway;
import com.fiap.techchallenge4.order.domain.entities.customer.payment.Payment;
import com.fiap.techchallenge4.order.infra.client.PaymentClient;
import com.fiap.techchallenge4.order.infra.client.request.ProcessPaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentClientGateway implements ProcessPaymentGateway {
    private final PaymentClient paymentClient;

    public PaymentClientGateway(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    @Override
    public boolean process(final Payment payment) {
        final var request = ProcessPaymentRequest.of(
                payment.getCardNumber(),
                payment.getExpirationDate().toString(),
                payment.getCode()
        );

        return paymentClient.processPayment(request).approved();
    }
}
