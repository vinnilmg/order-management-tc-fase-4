package com.fiap.techchallenge4.order.infra.client.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProviderAddressResponse(
        Long id,
        String street,
        Integer number,
        String complement,
        @JsonProperty("district")
        String neighborhood,
        String city,
        String state,
        @JsonProperty("cep")
        String postalCode
) {
}
