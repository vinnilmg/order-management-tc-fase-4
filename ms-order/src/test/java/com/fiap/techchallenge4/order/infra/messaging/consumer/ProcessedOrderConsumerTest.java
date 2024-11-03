package com.fiap.techchallenge4.order.infra.messaging.consumer;

import com.fiap.techchallenge4.order.application.usecases.order.ProcessOrderShippingUseCase;
import com.fiap.techchallenge4.order.domain.entities.order.OrderDomain;
import com.fiap.techchallenge4.order.infra.messaging.consumer.mappers.OrderDtoMapper;
import com.fiap.techchallenge4.order.utils.fixture.dto.OrderDtoFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProcessedOrderConsumerTest {
    private ProcessedOrderConsumer processedOrderConsumer;
    private ProcessOrderShippingUseCase processOrderShippingUseCase;
    private OrderDtoMapper orderDtoMapper;

    @BeforeEach
    void init() {
        processOrderShippingUseCase = mock(ProcessOrderShippingUseCase.class);
        orderDtoMapper = mock(OrderDtoMapper.class);
        processedOrderConsumer = new ProcessedOrderConsumer(
                orderDtoMapper,
                processOrderShippingUseCase
        );
    }

    @Test
    void shouldConsumeProcessedOrderMessage() {
        final var order = OrderDtoFixture.CRIADO();
        final var orderDomain = mock(OrderDomain.class);

        when(orderDtoMapper.toDomain(order))
                .thenReturn(orderDomain);

        doNothing()
                .when(processOrderShippingUseCase)
                .process(orderDomain);

        processedOrderConsumer.processedOrderConsumer(order);

        verify(orderDtoMapper).toDomain(order);
        verify(processOrderShippingUseCase).process(orderDomain);
    }
}
