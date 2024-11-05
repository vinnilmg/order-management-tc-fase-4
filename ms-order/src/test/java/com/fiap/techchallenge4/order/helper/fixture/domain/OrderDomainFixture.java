package com.fiap.techchallenge4.order.helper.fixture.domain;

import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.domain.entities.order.OrderDomain;
import com.fiap.techchallenge4.order.domain.enums.OrderStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.fiap.techchallenge4.order.helper.constants.OrderConstants.CPF;

public class OrderDomainFixture {

    public static Order CRIADO() {
        return OrderDomain.of(
                1L,
                CPF,
                OrderStatusEnum.CRIADO.name(),
                new BigDecimal("50"),
                LocalDateTime.now(),
                null,
                List.of(ProductDomainFixture.FULL()),
                ShippingDomainFixture.FULL()
        );
    }

}
