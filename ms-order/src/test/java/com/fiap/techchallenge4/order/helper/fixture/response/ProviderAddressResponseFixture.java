package com.fiap.techchallenge4.order.helper.fixture.response;

import com.fiap.techchallenge4.order.infra.client.response.ProviderAddressResponse;

import static com.fiap.techchallenge4.order.helper.constants.AddressConstants.POSTAL_CODE;

public class ProviderAddressResponseFixture {

    public static ProviderAddressResponse FULL() {
        return new ProviderAddressResponse(
                1L,
                "Rua dos vendedores",
                1,
                "Ap 01",
                "Vila dos padeiros",
                "São Paulo",
                "SP",
                POSTAL_CODE
        );
    }
}