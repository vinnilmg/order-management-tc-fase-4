package com.fiap.techchallenge4.order.helper.fixture.response;

import com.fiap.techchallenge4.order.infra.client.response.ProviderCustomerResponse;

import static com.fiap.techchallenge4.order.helper.constants.OrderConstants.CPF;

public class ProviderCustomerResponseFixture {

    public static ProviderCustomerResponse FULL() {
        return new ProviderCustomerResponse(
                1L,
                CPF,
                "Vinicius",
                "1999-01-01",
                ProviderAddressResponseFixture.FULL()
        );
    }
}
