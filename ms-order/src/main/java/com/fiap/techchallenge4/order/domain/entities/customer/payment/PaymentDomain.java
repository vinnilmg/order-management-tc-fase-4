package com.fiap.techchallenge4.order.domain.entities.customer.payment;

import com.fiap.techchallenge4.order.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.order.utils.DateUtils;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;

public class PaymentDomain implements Payment {
    private final String cardNumber;
    private final LocalDate expirationDate;
    private final String code;

    public PaymentDomain(
            final String cardNumber,
            final String expirationDate,
            final String code
    ) {
        requireNonNull(cardNumber, "Card Number cannot be null");
        if (cardNumber.length() != 16) throw CustomValidationException.of("Card Number", "is invalid");

        requireNonNull(expirationDate, "Expiration Date cannot be null");
        final var formattedExpirationDate = DateUtils.toDate(expirationDate);
        if (!formattedExpirationDate.isAfter(LocalDate.now()))
            throw CustomValidationException.of("Expiration Date", "must be a future date");

        requireNonNull(code, "Code cannot be null");
        if (code.length() < 3 && code.length() > 4) throw CustomValidationException.of("Code", "is invalid");

        this.cardNumber = cardNumber;
        this.expirationDate = formattedExpirationDate;
        this.code = code;
    }

    @Override
    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public String getCode() {
        return code;
    }
}
