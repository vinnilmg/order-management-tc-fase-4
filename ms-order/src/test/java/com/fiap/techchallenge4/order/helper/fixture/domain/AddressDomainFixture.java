package com.fiap.techchallenge4.order.helper.fixture.domain;

import com.fiap.techchallenge4.order.domain.entities.customer.address.AddressDomain;

public class AddressDomainFixture {

    public static AddressDomain FULL() {
        return new AddressDomain(
                1L,
                "Rua dos javeiros",
                69,
                "S/N",
                "Bairro do spring",
                "Cidade da versao oito",
                "São Lombok",
                "12345678"
        );
    }
}
