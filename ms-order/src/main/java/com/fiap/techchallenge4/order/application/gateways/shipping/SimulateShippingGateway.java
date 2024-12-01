package com.fiap.techchallenge4.order.application.gateways.shipping;

import com.fiap.techchallenge4.order.domain.entities.shipping.Shipping;

@FunctionalInterface
public interface SimulateShippingGateway {
    Shipping simulate(String postalCode);
}
