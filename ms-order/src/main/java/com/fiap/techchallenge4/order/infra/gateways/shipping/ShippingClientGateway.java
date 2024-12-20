package com.fiap.techchallenge4.order.infra.gateways.shipping;

import com.fiap.techchallenge4.order.application.gateways.shipping.CreateShippingGateway;
import com.fiap.techchallenge4.order.application.gateways.shipping.SimulateShippingGateway;
import com.fiap.techchallenge4.order.domain.entities.shipping.Shipping;
import com.fiap.techchallenge4.order.infra.client.ShippingClient;
import com.fiap.techchallenge4.order.infra.client.mappers.ProviderShippingResponseMapper;
import com.fiap.techchallenge4.order.infra.client.request.CreateShippingRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ShippingClientGateway implements SimulateShippingGateway, CreateShippingGateway {
    private final ShippingClient shippingClient;
    private final ProviderShippingResponseMapper providerShippingResponseMapper;

    public ShippingClientGateway(
            ShippingClient shippingClient,
            ProviderShippingResponseMapper providerShippingResponseMapper
    ) {
        this.shippingClient = shippingClient;
        this.providerShippingResponseMapper = providerShippingResponseMapper;
    }

    @Override
    public Shipping simulate(final String postalCode) {
        log.info("Simulando frete no microsserviço...");
        final var response = shippingClient.simulateShipping(postalCode);
        return providerShippingResponseMapper.toShippingDomain(response);
    }

    @Override
    public void create(final Long orderId, final String postalCode) {
        log.info("Criando nova entrega para o pedido no microsserviço...");
        shippingClient.createShipping(CreateShippingRequest.of(orderId, postalCode));
    }
}
