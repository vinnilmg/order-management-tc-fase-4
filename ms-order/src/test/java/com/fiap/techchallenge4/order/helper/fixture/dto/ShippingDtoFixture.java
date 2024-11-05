package com.fiap.techchallenge4.order.helper.fixture.dto;

import com.fiap.techchallenge4.order.infra.messaging.consumer.dto.ShippingDto;

import java.math.BigDecimal;

import static com.fiap.techchallenge4.order.helper.constants.AddressConstants.POSTAL_CODE;

public class ShippingDtoFixture {

    public static ShippingDto FULL() {
        return new ShippingDto(
                POSTAL_CODE,
                new BigDecimal("5"),
                3
        );
    }
}
