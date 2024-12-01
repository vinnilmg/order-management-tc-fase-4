package com.fiap.techchallenge4.order.infra.client.response;

public record ProviderCustomerResponse(
        Long id,
        String cpf,
        String name,
        String birthDate,
        ProviderAddressResponse address
) {
}
