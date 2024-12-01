package com.fiap.techchallenge4.order.infra.controllers.mappers;

import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.domain.enums.OrderStatusEnum;
import com.fiap.techchallenge4.order.helper.fixture.request.OrderRequestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderRequestMapperTest {
    private OrderRequestMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new OrderRequestMapperImpl();
    }

    @Test
    void shouldMapOrder() {
        final var request = OrderRequestFixture.FULL();
        final var result = mapper.toOrder(request);
        assertThat(result)
                .isNotNull()
                .extracting(Order::getCpf, Order::getStatus)
                .containsExactly(request.cpf(), OrderStatusEnum.CRIADO.name());

        assertThat(result.getProducts())
                .isNotEmpty()
                .hasSize(request.products().size());
    }
}
