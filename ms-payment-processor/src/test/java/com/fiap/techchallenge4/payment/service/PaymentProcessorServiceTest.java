package com.fiap.techchallenge4.payment.service;

import com.fiap.techchallenge4.payment.helper.fixture.request.PaymentDataRequestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaymentProcessorServiceTest {
    private PaymentProcessorService service;

    @BeforeEach
    void setup() {
        service = new PaymentProcessorServiceImpl();
    }

    @Test
    void shouldProcessPaymentAndReturnApproved() {
        final var request = PaymentDataRequestFixture.VALID();
        final var result = service.processPayment(request);
        assertThat(result.approved())
                .isTrue();
    }

    @Test
    void shouldProcessPaymentAndReturnUnapproved() {
        final var request = PaymentDataRequestFixture.UNAPPROVED();
        final var result = service.processPayment(request);
        assertThat(result.approved())
                .isFalse();
    }

    @Test
    void shouldThrowNullPointerExceptionWhenRequestIsNull() {
        assertThatThrownBy(() -> service.processPayment(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Request cannot be null");
    }

    @Test
    void shouldThrowNullPointerExceptionWhenCardNumberIsNull() {
        final var request = PaymentDataRequestFixture.WITHOUT_CARD_NUMBER();
        assertThatThrownBy(() -> service.processPayment(request))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Card Number cannot be null");
    }

    @Test
    void shouldThrowNullPointerExceptionWhenExpirationDateIsNull() {
        final var request = PaymentDataRequestFixture.WITHOUT_EXPIRATION_DATE();
        assertThatThrownBy(() -> service.processPayment(request))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Expiration Date cannot be null");
    }

    @Test
    void shouldThrowNullPointerExceptionWhenCodeIsNull() {
        final var request = PaymentDataRequestFixture.WITHOUT_CODE();
        assertThatThrownBy(() -> service.processPayment(request))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Code cannot be null");
    }
}
