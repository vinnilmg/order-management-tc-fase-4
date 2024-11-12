package com.fiap.techchallenge4.order.helper.fixture.entity;

import com.fiap.techchallenge4.order.infra.persistence.entities.OrderEntity;
import com.fiap.techchallenge4.order.infra.persistence.entities.ProductEntity;

import java.math.BigDecimal;
import java.util.List;

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

    public static ProductEntity FULL() {
        final var result = new ProductEntity();
        result.setId(1L);
        result.setSku(3000L);
        result.setOrder(OrderEntityFixture.CRIADO());
        result.setUnitaryValue(new BigDecimal(50));
        result.setPurchaseQuantity(1);
        return result;
    }

    public static List<ProductEntity> PRODUCTS() {
        return List.of(FULL());
    }
}
