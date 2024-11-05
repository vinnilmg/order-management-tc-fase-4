package com.fiap.techchallenge4.order.utils.fixture.entity;

import com.fiap.techchallenge4.order.infra.persistence.entities.OrderEntity;
import com.fiap.techchallenge4.order.infra.persistence.entities.ShippingEntity;

import java.math.BigDecimal;

import static com.fiap.techchallenge4.order.utils.constants.AddressConstants.POSTAL_CODE;

public class ShippingEntityFixture {

    public static ShippingEntity FULL(final OrderEntity order) {
        final var result = new ShippingEntity();
        result.setId(1L);
        result.setOrder(order);
        result.setPrice(new BigDecimal(5));
        result.setPostalCode(POSTAL_CODE);
        result.setEstimate(3);
        result.setTimeUnit("DAY");
        return result;
    }
}