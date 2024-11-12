package com.fiap.techchallenge4.order.helper.fixture.response;

import com.fiap.techchallenge4.order.infra.client.response.ProviderShippingResponse;

import java.math.BigDecimal;

import static com.fiap.techchallenge4.order.helper.constants.AddressConstants.POSTAL_CODE;

public class ProviderShippingResponseFixture {

    public static ProviderShippingResponse FULL() {
        return new ProviderShippingResponse(
                POSTAL_CODE,
                new BigDecimal(5),
                3
        );
    }
}
