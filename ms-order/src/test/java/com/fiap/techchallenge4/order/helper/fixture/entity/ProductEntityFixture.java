package com.fiap.techchallenge4.order.helper.fixture.entity;

import com.fiap.techchallenge4.order.infra.persistence.entities.OrderEntity;
import com.fiap.techchallenge4.order.infra.persistence.entities.ProductEntity;

import java.math.BigDecimal;

public class ProductEntityFixture {

    public static ProductEntity FULL(final OrderEntity order) {
        final var result = new ProductEntity();
        result.setId(1L);
        result.setSku(3000L);
        result.setOrder(order);
        result.setUnitaryValue(new BigDecimal(50));
        result.setPurchaseQuantity(1);
        return result;
    }
}
