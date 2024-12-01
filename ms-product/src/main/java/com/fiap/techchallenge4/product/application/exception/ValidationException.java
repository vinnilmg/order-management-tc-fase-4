package com.fiap.techchallenge4.product.application.exception;

import static java.lang.String.format;

public class ValidationException extends RuntimeException {

    private final String field;
    private final String message;

    public ValidationException(final String field, final String message) {
        super(message);
        this.field = field;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return format("%s %s", field, message);
    }

    public static ValidationException of(final String field, final String message) {
        return new ValidationException(field, message);
    }
}
