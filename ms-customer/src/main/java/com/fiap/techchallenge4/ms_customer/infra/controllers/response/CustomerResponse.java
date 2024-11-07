package com.fiap.techchallenge4.ms_customer.infra.controllers.response;

import com.fiap.techchallenge4.ms_customer.domain.entities.address.Address;

public record CustomerResponse(
        Long id,
        String cpf,
        String name,
        Address address,
        String birthDate
) {
}
