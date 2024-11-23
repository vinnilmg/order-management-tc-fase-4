package com.fiap.techchallenge4.order.helper.fixture.response;

import com.fiap.techchallenge4.order.infra.client.response.ProviderPaymentProcessedResponse;

public class ProviderPaymentProcessedResponseFixture {

    public static ProviderPaymentProcessedResponse APPROVED() {
        return new ProviderPaymentProcessedResponse(true);
    }

    public static ProviderPaymentProcessedResponse NOT_APPROVED() {
        return new ProviderPaymentProcessedResponse(false);
    }
}
