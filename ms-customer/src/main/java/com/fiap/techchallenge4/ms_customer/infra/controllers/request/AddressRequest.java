package com.fiap.techchallenge4.ms_customer.infra.controllers.request;

public record AddressRequest(
        String street,
        String number,
        String complement,
        String district,
        String city,
        String state,
        String cep,
        Long customerId
) {
}
