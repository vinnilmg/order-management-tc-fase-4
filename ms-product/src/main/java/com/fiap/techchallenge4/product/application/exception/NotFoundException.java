package com.fiap.techchallenge4.product.application.exception;

public class NotFoundException extends RuntimeException {

    private final String resource;

    public NotFoundException(final String resource) {
        super(resource);
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }

    @Override
    public String getMessage() {
        return this.resource;
    }

    public static NotFoundException of(final String resource) {
        return new NotFoundException(resource);
    }
}
