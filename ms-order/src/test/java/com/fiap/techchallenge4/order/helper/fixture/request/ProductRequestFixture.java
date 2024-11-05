package com.fiap.techchallenge4.order.helper.fixture.request;

import com.fiap.techchallenge4.order.infra.controllers.request.ProductRequest;

import java.math.BigDecimal;

public class ProductRequestFixture {

    public static ProductRequest FULL() {
        return new ProductRequest(
                3000L,
                1,
                new BigDecimal(10)
        );
    }
}
