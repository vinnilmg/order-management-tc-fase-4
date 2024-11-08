package com.fiap.techchallenge4.order.helper.fixture.domain;

import com.fiap.techchallenge4.order.domain.entities.product.Product;
import com.fiap.techchallenge4.order.domain.entities.product.ProductDomain;

import java.math.BigDecimal;

public class ProductDomainFixture {

    public static Product FULL() {
        return ProductDomain.of(
                1L,
                3000L,
                1,
                new BigDecimal(50)
        );
    }
}
