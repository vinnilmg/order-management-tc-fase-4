package com.fiap.techchallenge4.ms_shipping.config.exception;

import lombok.Getter;

import static java.lang.String.format;


@Getter
public class NotFoundExpection  extends RuntimeException {
    private static final String DEFAULT_MSG = "not found";

    private String resource;
    public NotFoundExpection(String resource) {
        super(DEFAULT_MSG);
        this.resource = resource;
    }

    @Override
    public String getMessage(){
        return format("%s %s", resource, "Not found");
    }

    public static NotFoundExpection of(final String resource) {
        return new NotFoundExpection(resource);
    }
}
