package com.fiap.techchallenge4.order.helper.fixture.dto;

import com.fiap.techchallenge4.order.infra.messaging.consumer.dto.ProductDto;

import java.math.BigDecimal;

public class ProductDtoFixture {

    public static ProductDto FULL() {
        return new ProductDto(
                1L,
                3000L,
                1,
                new BigDecimal("50").setScale(2),
                new BigDecimal("50").setScale(2)
        );
    }
}
