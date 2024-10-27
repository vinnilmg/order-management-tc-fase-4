package com.fiap.techchallenge4.order.infra.gateways.shipping;

import com.fiap.techchallenge4.order.application.gateways.shipping.SimulateShippingGateway;
import com.fiap.techchallenge4.order.domain.entities.shipping.Shipping;
import com.fiap.techchallenge4.order.infra.client.ShippingClient;
import com.fiap.techchallenge4.order.infra.client.mappers.ProviderShippingResponseMapper;
import com.fiap.techchallenge4.order.infra.client.request.SimulateShippingRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ShippingClientGateway implements SimulateShippingGateway {
    private final ShippingClient shippingClient;
    private final ProviderShippingResponseMapper providerShippingResponseMapper;

    public ShippingClientGateway(ShippingClient shippingClient, ProviderShippingResponseMapper providerShippingResponseMapper) {
        this.shippingClient = shippingClient;
        this.providerShippingResponseMapper = providerShippingResponseMapper;
    }

    @Override
    public Shipping simulate(final String postalCode) {
        log.info("Simulando frete no microsserviço...");
        final var response = shippingClient.simulateShipping(SimulateShippingRequest.of(postalCode));
        return providerShippingResponseMapper.toShippingDomain(response);
    }
}
