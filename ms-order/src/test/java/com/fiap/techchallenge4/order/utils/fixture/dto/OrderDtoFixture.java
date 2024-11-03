package com.fiap.techchallenge4.order.utils.fixture.dto;

import com.fiap.techchallenge4.order.domain.enums.OrderStatusEnum;
import com.fiap.techchallenge4.order.infra.messaging.consumer.dto.OrderDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.fiap.techchallenge4.order.utils.constants.OrderConstants.CPF;

public class OrderDtoFixture {

    public static OrderDto CRIADO() {
        return new OrderDto(
                1L,
                CPF,
                OrderStatusEnum.CRIADO.name(),
                LocalDateTime.now(),
                List.of(ProductDtoFixture.FULL()),
                ShippingDtoFixture.FULL(),
                new BigDecimal("50"),
                new BigDecimal("55")
        );
    }
}
