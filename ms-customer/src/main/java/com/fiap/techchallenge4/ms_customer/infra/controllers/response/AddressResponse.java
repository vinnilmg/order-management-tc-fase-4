package com.fiap.techchallenge4.ms_customer.infra.controllers.response;

public record AddressResponse(
        Long id,
        String street,
        String number,
        String complement,
        String district,
        String city,
        String state,
        String cep) {
}
