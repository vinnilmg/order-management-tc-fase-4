package com.fiap.techchallenge4.order.helper.fixture.response;

import com.fiap.techchallenge4.order.infra.client.response.ProviderPaymentInfoResponse;

public class ProviderPaymentInfoResponseFixture {

    public static ProviderPaymentInfoResponse FULL() {
        return new ProviderPaymentInfoResponse(
                "0001234567891234",
                "2030-01-01",
                "321"
        );
    }
}
