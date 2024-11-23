package com.fiap.techchallenge4.ms_customer.helper.fixture.request;

import com.fiap.techchallenge4.ms_customer.infra.controllers.request.AddressRequest;

public class AddressRequestFixture {
    public static AddressRequest FULL() {
        return new AddressRequest(
                "Rua São Lourenço",
                "33",
                null,
                "Lagoa do Mato",
                "Mossoró",
                "RN",
                "59604230",
                1L
        );
    }
}
