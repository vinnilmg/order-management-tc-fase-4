package com.fiap.techchallenge4.order.application.gateways.shipping;

@FunctionalInterface
public interface CreateShippingGateway {
    void create(Long orderId, String postalCode);
}
