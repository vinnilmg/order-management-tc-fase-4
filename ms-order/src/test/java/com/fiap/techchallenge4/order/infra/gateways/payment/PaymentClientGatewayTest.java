package com.fiap.techchallenge4.order.infra.gateways.payment;

import com.fiap.techchallenge4.order.helper.fixture.domain.PaymentDomainFixture;
import com.fiap.techchallenge4.order.helper.fixture.exception.FeignExceptionFixture;
import com.fiap.techchallenge4.order.helper.fixture.response.ProviderPaymentProcessedResponseFixture;
import com.fiap.techchallenge4.order.infra.client.PaymentClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class PaymentClientGatewayTest {
    @InjectMocks
    private PaymentClientGateway paymentClientGateway;
    @Mock
    private PaymentClient paymentClient;

    @Test
    void shouldProcessPaymentAndReturnTrue() {
        final var payment = PaymentDomainFixture.FULL();

        when(paymentClient.processPayment(any()))
                .thenReturn(ProviderPaymentProcessedResponseFixture.APPROVED());

        final var result = paymentClientGateway.process(payment);
        assertThat(result)
                .isTrue();

        verify(paymentClient).processPayment(any());
    }

    @Test
    void shouldProcessPaymentAndReturnFalse() {
        final var payment = PaymentDomainFixture.FULL();

        when(paymentClient.processPayment(any()))
                .thenReturn(ProviderPaymentProcessedResponseFixture.NOT_APPROVED());

        final var result = paymentClientGateway.process(payment);
        assertThat(result)
                .isFalse();

        verify(paymentClient).processPayment(any());
    }

    @Test
    void shouldReturnFalseWhenAnyErrorOccurs() {
        final var payment = PaymentDomainFixture.FULL();

        when(paymentClient.processPayment(any()))
                .thenThrow(FeignExceptionFixture.INTERNAL_SERVER_ERROR());

        final var result = paymentClientGateway.process(payment);
        assertThat(result)
                .isFalse();

        verify(paymentClient).processPayment(any());
    }
}
