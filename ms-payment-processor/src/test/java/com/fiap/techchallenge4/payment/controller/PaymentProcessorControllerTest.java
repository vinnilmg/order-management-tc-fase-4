package com.fiap.techchallenge4.payment.controller;

import com.fiap.techchallenge4.payment.helper.fixture.request.PaymentDataRequestFixture;
import com.fiap.techchallenge4.payment.helper.fixture.response.PaymentProcessorResponseFixture;
import com.fiap.techchallenge4.payment.service.PaymentProcessorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentProcessorControllerTest {
    @InjectMocks
    private PaymentProcessorController paymentProcessorController;
    @Mock
    private PaymentProcessorServiceImpl paymentProcessorService;

    @Test
    void shouldProcessPayment() {
        final var request = PaymentDataRequestFixture.VALID();

        when(paymentProcessorService.processPayment(request))
                .thenReturn(PaymentProcessorResponseFixture.APPROVED());

        final var result = paymentProcessorController.process(request);

        assertThat(result.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        verify(paymentProcessorService).processPayment(request);
    }
}
