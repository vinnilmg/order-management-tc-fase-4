package com.fiap.techchallenge4.order.application.gateways.product;

@FunctionalInterface
public interface AddStockGateway {
    void add(Long sku, Integer quantity);
}
