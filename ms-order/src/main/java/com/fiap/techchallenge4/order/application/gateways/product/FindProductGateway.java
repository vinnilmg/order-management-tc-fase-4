package com.fiap.techchallenge4.order.application.gateways.product;

import com.fiap.techchallenge4.order.domain.entities.product.Product;

import java.util.Optional;

@FunctionalInterface
public interface FindProductGateway {
    Optional<Product> find(Long sku);
}
