package com.fiap.techchallenge4.order.helper.fixture.exception;

import feign.FeignException;
import feign.Request;

import static org.mockito.Mockito.mock;

public class FeignExceptionFixture {

    public static FeignException NOT_FOUND() {
        return new FeignException.NotFound("message", mock(Request.class), null, null);
    }

    public static FeignException INTERNAL_SERVER_ERROR() {
        return new FeignException.InternalServerError("message", mock(Request.class), null, null);
    }
}
