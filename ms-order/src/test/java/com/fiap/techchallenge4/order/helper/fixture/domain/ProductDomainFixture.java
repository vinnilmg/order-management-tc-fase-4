package com.fiap.techchallenge4.order.helper.fixture.domain;

import com.fiap.techchallenge4.order.domain.entities.product.Product;
import com.fiap.techchallenge4.order.domain.entities.product.ProductDomain;

import java.math.BigDecimal;
import java.util.List;

public class ProductDomainFixture {

    public static Product FULL() {
        return ProductDomain.of(
                1L,
                3000L,
                1,
                new BigDecimal(50)
        );
    }

    public static Product WITH_ONE_QUANTITY() {
        return ProductDomain.of(
                1L,
                3000L,
                1,
                new BigDecimal(50)
        );
    }

    public static Product WITH_TWO_QUANTITY() {
        return ProductDomain.of(
                1L,
                3000L,
                2,
                new BigDecimal(50)
        );
    }

    public static Product SKU_3001() {
        return ProductDomain.of(
                1L,
                3001L,
                1,
                new BigDecimal(100)
        );
    }

    public static List<Product> PRODUCTS() {
        return List.of(FULL());
    }
}
