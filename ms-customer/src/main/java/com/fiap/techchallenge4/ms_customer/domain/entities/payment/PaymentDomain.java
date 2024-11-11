package com.fiap.techchallenge4.ms_customer.domain.entities.payment;

import com.fiap.techchallenge4.ms_customer.domain.exceptions.CustomValidationException;

import java.time.LocalDate;

import static java.util.Objects.isNull;

public class PaymentDomain implements Payment {
    private Long id;
    private String cardNumber;
    private LocalDate expirationDate;
    private String cvvCode;

    public static Payment of(
            final Long id,
            final String cardNumber,
            final LocalDate expirationDate,
            final String cvvCode
    ) {
        return new PaymentDomain(
                id,
                cardNumber,
                expirationDate,
                cvvCode
        );
    }

    public PaymentDomain(
            final String cardNumber,
            final LocalDate expirationDate,
            final String cvvCode
    ) {
        this.cardNumber = cardNumberValidation(cardNumber);
        this.expirationDate = expirationDateValidation(expirationDate);
        this.cvvCode = cvvCodeValidation(cvvCode);
    }

    public PaymentDomain(
            final Long id,
            final String cardNumber,
            final LocalDate expirationDate,
            final String cvvCode
    ) {
        this.id = idValidation(id);
        this.cardNumber = cardNumberValidation(cardNumber);
        this.expirationDate = expirationDateValidation(expirationDate);
        this.cvvCode = cvvCodeValidation(cvvCode);
    }

    @Override
    public Long getId() {
        return id;
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
    public String getCvvCode() {
        return cvvCode;
    }

    private static Long idValidation(final Long id) {
        if (isNull(id)) throw CustomValidationException.of("Payment Id", "cannot be null");
        if (id < 0) throw CustomValidationException.of("Payment Id", "cannot be negative");
        return id;
    }

    private static String cardNumberValidation(final String cardNumber) {
        if (isNull(cardNumber)) throw CustomValidationException.of("Payment Card Number", "cannot be null");
        if (cardNumber.length() < 13 || cardNumber.length() > 16) throw CustomValidationException.of(
                "Payment Card Number", "must be between 13 and 16 positions");
        return cardNumber;
    }

    private static LocalDate expirationDateValidation(final LocalDate expirationDate) {
        if (isNull(expirationDate)) throw CustomValidationException.of(
                "Payment Expiration Date", "cannot be null");
        LocalDate currentDate = LocalDate.now();
        if (expirationDate.isBefore(currentDate) || expirationDate.isEqual(currentDate))
            throw CustomValidationException.of("Payment Expiration Date", "must be a future date.");
        return expirationDate;
    }

    private static String cvvCodeValidation(final String cvvCode) {
        if (isNull(cvvCode)) throw CustomValidationException.of("Payment CVV Code", "cannot be null");
        if (cvvCode.length() != 3) throw CustomValidationException.of(
                "Payment CVV Code", "must be 3 positions");
        return cvvCode;
    }
}
