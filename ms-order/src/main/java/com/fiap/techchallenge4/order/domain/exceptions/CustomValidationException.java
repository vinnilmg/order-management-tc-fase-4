package com.fiap.techchallenge4.order.domain.exceptions;

import lombok.Getter;

import static java.text.MessageFormat.format;

public class CustomValidationException extends RuntimeException {
    @Getter
    private final String field;
    private final String message;

    public CustomValidationException(final String field, final String message) {
        super(message);
        this.field = field;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return format("{0} {1}", field, message);
    }

    public static CustomValidationException of(final String field, final String message) {
        return new CustomValidationException(field, message);
    }
}
