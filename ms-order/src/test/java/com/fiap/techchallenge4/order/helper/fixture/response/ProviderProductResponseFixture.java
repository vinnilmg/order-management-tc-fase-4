package com.fiap.techchallenge4.order.helper.fixture.response;

import com.fiap.techchallenge4.order.infra.client.response.ProviderProductResponse;

import java.math.BigDecimal;

public class ProviderProductResponseFixture {

    public static ProviderProductResponse FULL() {
        return new ProviderProductResponse(
                1L,
                3000L,
                "Camiseta",
                new BigDecimal("24.99"),
                5
        );
    }

    public static ProviderProductResponse WITH_PRICE_TEN() {
        return new ProviderProductResponse(
                1L,
                3000L,
                "Camiseta",
                new BigDecimal(10),
                5
        );
    }
}
