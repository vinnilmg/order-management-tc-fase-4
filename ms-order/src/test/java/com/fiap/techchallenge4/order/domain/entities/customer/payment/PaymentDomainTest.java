package com.fiap.techchallenge4.order.domain.entities.customer.payment;

import com.fiap.techchallenge4.order.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.order.utils.DateUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaymentDomainTest {

    @Test
    void givenACardNumberNullWhenConstructThenThrowNullPointerException() {
        assertThatThrownBy(() -> new PaymentDomain(null, null, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Card Number cannot be null");
    }

    @Test
    void givenAnInvalidCardNumberWhenConstructThenThrowCustomValidationException() {
        assertThatThrownBy(() -> new PaymentDomain("12345678", null, null))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Card Number is invalid");
    }

    @Test
    void givenAnExpirationDateNullWhenConstructThenThrowNullPointerException() {
        assertThatThrownBy(() -> new PaymentDomain("1234567891234567", null, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Expiration Date cannot be null");
    }

    @Test
    void givenAnInvalidExpirationDateWhenConstructThenThrowCustomValidationException() {
        assertThatThrownBy(() -> new PaymentDomain("1234567891234567", "2019-01-01", null))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Expiration Date must be a future date");
    }

    @Test
    void givenACodeNullWhenConstructThenThrowNullPointerException() {
        assertThatThrownBy(() -> new PaymentDomain("1234567891234567", "2030-01-01", null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Code cannot be null");
    }

    @Test
    void givenAnInvalidCodeWhenConstructThenThrowCustomValidationException() {
        assertThatThrownBy(() -> new PaymentDomain("1234567891234567", "2030-01-01", "123456"))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Code is invalid");
    }

    @Test
    void shouldConstructPaymentDomain() {
        final var cardNumber = "1234567891234567";
        final var expirationDate = "2030-01-01";
        final var code = "1234";
        final var result = new PaymentDomain(cardNumber, expirationDate, code);
        assertThat(result)
                .isNotNull()
                .extracting(Payment::getCardNumber, Payment::getExpirationDate, Payment::getCode)
                .containsExactly(cardNumber, DateUtils.toDate(expirationDate), code);
    }
}
