package com.fiap.techchallenge4.order.helper.fixture.request;

import com.fiap.techchallenge4.order.infra.controllers.request.OrderRequest;

import java.util.List;

import static com.fiap.techchallenge4.order.helper.constants.OrderConstants.CPF;

public class OrderRequestFixture {

    public static OrderRequest FULL() {
        return new OrderRequest(
                CPF,
                List.of(ProductRequestFixture.FULL())
        );
    }
}
