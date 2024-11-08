package com.fiap.techchallenge4.ms_customer.infra.controllers.request;

public record CustomerRequest(
        String cpf,
        String name,
        AddressRequest address,
        String birthDate
) {
}
