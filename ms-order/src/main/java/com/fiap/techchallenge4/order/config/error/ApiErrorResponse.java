package com.fiap.techchallenge4.order.config.error;

public record ApiErrorResponse(
        String type,
        String message
) {
    public static ApiErrorResponse of(final String type, final String message) {
        return new ApiErrorResponse(type, message);
    }
}
