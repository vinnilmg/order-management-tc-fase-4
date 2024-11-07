package com.fiap.techchallenge4.product.application.exception;

import static java.lang.String.format;

public class NotFoundException extends RuntimeException {
    private static final String DEFAULT_MSG = "not found";

    private final String resource;

    public NotFoundException(final String resource) {
        super(DEFAULT_MSG);
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }

    @Override
    public String getMessage() {
        return format("%s %s", resource, super.getMessage());
    }

    public static NotFoundException of(final String resource) {
        return new NotFoundException(resource);
    }
}
