package com.fiap.techchallenge4.ms_customer.infra.controllers.response;

import com.fiap.techchallenge4.ms_customer.domain.entities.address.Address;
import com.fiap.techchallenge4.ms_customer.domain.entities.payment.Payment;

public record CustomerResponse(
        Long id,
        String cpf,
        String name,
        Address address,
        String birthDate,
        Payment payment
) {
}
