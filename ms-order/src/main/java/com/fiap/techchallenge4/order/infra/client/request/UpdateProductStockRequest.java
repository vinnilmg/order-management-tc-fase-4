package com.fiap.techchallenge4.order.infra.client.request;

public record UpdateProductStockRequest(Integer quantity) {

    public static UpdateProductStockRequest of(final Integer quantity) {
        return new UpdateProductStockRequest(quantity);
    };
}
