package com.fiap.techchallenge4.order.utils.fixture.entity;

import com.fiap.techchallenge4.order.domain.enums.OrderStatusEnum;
import com.fiap.techchallenge4.order.infra.persistence.entities.OrderEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.fiap.techchallenge4.order.utils.constants.OrderConstants.CPF;

public class OrderEntityFixture {

    public static OrderEntity CRIADO() {
        final var result = new OrderEntity();
        result.setId(1L);
        result.setCpf(CPF);
        result.setStatus(OrderStatusEnum.CRIADO.name());
        result.setTotal(new BigDecimal(50));
        result.setOrderCreationDate(LocalDateTime.now());
        result.setOrderCompletionDate(null);
        result.setShipping(ShippingEntityFixture.FULL(result));
        result.setProducts(List.of(ProductEntityFixture.FULL(result)));
        return result;
    }
}
