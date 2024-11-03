package com.fiap.techchallenge4.order.infra.messaging.consumer;

import com.fiap.techchallenge4.order.application.usecases.order.ValidateOrderUseCase;
import com.fiap.techchallenge4.order.domain.entities.order.OrderDomain;
import com.fiap.techchallenge4.order.infra.messaging.consumer.mappers.OrderDtoMapper;
import com.fiap.techchallenge4.order.utils.fixture.dto.OrderDtoFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreatedOrderConsumerTest {
    private CreatedOrderConsumer createdOrderConsumer;
    private ValidateOrderUseCase validateOrderUseCase;
    private OrderDtoMapper orderDtoMapper;

    @BeforeEach
    void init() {
        validateOrderUseCase = mock(ValidateOrderUseCase.class);
        orderDtoMapper = mock(OrderDtoMapper.class);
        createdOrderConsumer = new CreatedOrderConsumer(
                validateOrderUseCase,
                orderDtoMapper
        );
    }

    @Test
    void shouldConsumeCreatedOrderMessage() {
        final var order = OrderDtoFixture.CRIADO();
        final var orderDomain = mock(OrderDomain.class);

        when(orderDtoMapper.toDomain(order))
                .thenReturn(orderDomain);

        doNothing()
                .when(validateOrderUseCase)
                .validate(orderDomain);

        createdOrderConsumer.orderCreationConsumer(order);

        verify(orderDtoMapper).toDomain(order);
        verify(validateOrderUseCase).validate(orderDomain);
    }
}
