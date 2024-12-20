package com.fiap.techchallenge4.ms_customer.domain.exceptions;

import static java.text.MessageFormat.format;

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
        return format("{0} {1}", resource, super.getMessage());
    }

    public static NotFoundException of(final String resource) {
        return new NotFoundException(resource);
    }
}
