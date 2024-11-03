package com.fiap.techchallenge4.order.infra.client.request;

public record CreateShippingRequest(
        Long orderId,
        String postalCode
) {
    public static CreateShippingRequest of(final Long orderId, final String postalCode) {
        return new CreateShippingRequest(orderId, postalCode);
    }
}
