package com.fiap.techchallenge4.order.infra.messaging.consumer.mappers;

import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.domain.entities.product.Product;
import com.fiap.techchallenge4.order.domain.entities.shipping.Shipping;
import com.fiap.techchallenge4.order.utils.fixture.dto.OrderDtoFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderDtoMapperTest {
    private OrderDtoMapper mapper;

    @BeforeEach
    void init() {
        mapper = new OrderDtoMapperImpl();
    }

    @Test
    void shouldMapOrderDomain() {
        final var order = OrderDtoFixture.CRIADO();
        final var result = mapper.toDomain(order);
        assertThat(result)
                .isNotNull()
                .extracting(
                        Order::getId,
                        Order::getCpf,
                        Order::getStatus,
                        Order::getCreationDate,
                        Order::getTotal,
                        Order::getCompletionDate
                )
                .containsExactly(
                        order.getId(),
                        order.getCpf(),
                        order.getStatus(),
                        order.getCreationDate(),
                        order.getTotal(),
                        null
                );

        assertThat(result.getProducts())
                .isNotEmpty()
                .allSatisfy(product -> order.getProducts()
                        .stream()
                        .filter(dto -> dto.getId().equals(product.getId()))
                        .findFirst()
                        .ifPresent(dto -> assertThat(product)
                                .isNotNull()
                                .extracting(
                                        Product::getId,
                                        Product::getSku,
                                        Product::getQuantity,
                                        Product::getValue,
                                        Product::getTotal
                                ).containsExactly(
                                        dto.getId(),
                                        dto.getSku(),
                                        dto.getQuantity(),
                                        dto.getValue(),
                                        dto.getTotal())
                        ));

        assertThat(result.getShipping())
                .extracting(
                        Shipping::getPostalCode,
                        Shipping::getPrice,
                        Shipping::getDaysForDelivery
                ).containsExactly(
                        order.getShipping().getPostalCode(),
                        order.getShipping().getPrice(),
                        order.getShipping().getDaysForDelivery()
                );
    }
}
