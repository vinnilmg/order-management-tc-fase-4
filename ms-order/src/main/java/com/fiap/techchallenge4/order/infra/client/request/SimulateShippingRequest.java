package com.fiap.techchallenge4.order.infra.client.request;

public record SimulateShippingRequest(String postalCode) {

    public static SimulateShippingRequest of(final String postalCode) {
        return new SimulateShippingRequest(postalCode);
    }
}
