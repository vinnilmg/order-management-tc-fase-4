package com.fiap.techchallenge4.order.application.gateways.product;

@FunctionalInterface
public interface DecreaseStockGateway {
    void decrease(Long sku, Integer quantity);
}
