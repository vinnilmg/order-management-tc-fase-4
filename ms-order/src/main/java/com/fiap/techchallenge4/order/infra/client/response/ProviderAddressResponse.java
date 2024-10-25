package com.fiap.techchallenge4.order.infra.client.response;

public record ProviderAddressResponse(
        Long id,
        String street,
        Integer number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String postalCode
) {
}
