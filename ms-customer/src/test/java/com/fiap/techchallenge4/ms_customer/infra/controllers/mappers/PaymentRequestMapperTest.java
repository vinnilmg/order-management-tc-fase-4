package com.fiap.techchallenge4.ms_customer.infra.controllers.mappers;

import com.fiap.techchallenge4.ms_customer.domain.entities.payment.Payment;
import com.fiap.techchallenge4.ms_customer.helper.fixture.request.PaymentRequestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentRequestMapperTest {
    private PaymentRequestMapper paymentRequestMapper;

    @BeforeEach
    void setup() {
        paymentRequestMapper = new PaymentRequestMapperImpl();
    }

    @Test
    void shouldMapPaymentRequest() {
        final var paymentRequest = PaymentRequestFixture.FULL();
        final var result = paymentRequestMapper.toPayment(paymentRequest);

        assertThat(result)
                .isNotNull()
                .extracting(
                        Payment::getCardNumber,
                        Payment::getCvvCode,
                        Payment::getExpirationDate
                ).containsExactly(
                        paymentRequest.cardNumber(),
                        paymentRequest.cvvCode(),
                        paymentRequest.expirationDate());

    }
}
