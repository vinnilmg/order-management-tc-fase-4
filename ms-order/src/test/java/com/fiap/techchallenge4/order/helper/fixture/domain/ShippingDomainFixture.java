package com.fiap.techchallenge4.order.helper.fixture.domain;

import com.fiap.techchallenge4.order.domain.entities.shipping.Shipping;
import com.fiap.techchallenge4.order.domain.entities.shipping.ShippingDomain;

import java.math.BigDecimal;

import static com.fiap.techchallenge4.order.helper.constants.AddressConstants.POSTAL_CODE;

public class ShippingDomainFixture {

    public static Shipping FULL() {
        return new ShippingDomain(
                POSTAL_CODE,
                new BigDecimal(5),
                3
        );
    }
}
