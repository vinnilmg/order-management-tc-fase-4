package com.fiap.techchallenge4.ms_customer.infra.controllers.mappers;

import com.fiap.techchallenge4.ms_customer.helper.constants.CustomerConstants;
import com.fiap.techchallenge4.ms_customer.infra.controllers.response.PaymentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentResponseMapperTest {
    private PaymentResponseMapper paymentResponseMapper;

    @BeforeEach
    void setup() {
        paymentResponseMapper = new PaymentResponseMapperImpl();
    }

    @Test
    void shouldMapPaymentResponse() {
        final var payment = CustomerConstants.DEFAULT_PAYMENT_DOMAIN;
        final var result = paymentResponseMapper.toResponse(payment);

        assertThat(result)
                .isNotNull()
                .extracting(
                        PaymentResponse::id,
                        PaymentResponse::cardNumber,
                        PaymentResponse::cvvCode,
                        PaymentResponse::expirationDate
                ).containsExactly(
                        payment.getId(),
                        payment.getCardNumber(),
                        payment.getCvvCode(),
                        payment.getExpirationDate());
    }
}
