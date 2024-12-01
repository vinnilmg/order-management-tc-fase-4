package com.fiap.techchallenge4.order.infra.messaging.consumer;

import com.fiap.techchallenge4.order.application.usecases.order.ProcessOrderPaymentUseCase;
import com.fiap.techchallenge4.order.domain.entities.order.OrderDomain;
import com.fiap.techchallenge4.order.infra.messaging.consumer.mappers.OrderDtoMapper;
import com.fiap.techchallenge4.order.helper.fixture.dto.OrderDtoFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PendingPaymentOrderConsumerTest {
    private PendingPaymentOrderConsumer pendingPaymentOrderConsumer;
    private ProcessOrderPaymentUseCase processOrderPaymentUseCase;
    private OrderDtoMapper orderDtoMapper;

    @BeforeEach
    void init() {
        processOrderPaymentUseCase = mock(ProcessOrderPaymentUseCase.class);
        orderDtoMapper = mock(OrderDtoMapper.class);
        pendingPaymentOrderConsumer = new PendingPaymentOrderConsumer(
                processOrderPaymentUseCase,
                orderDtoMapper
        );
    }

    @Test
    void shouldConsumeOrderPendingPaymentMessage() {
        final var order = OrderDtoFixture.CRIADO();
        final var orderDomain = mock(OrderDomain.class);

        when(orderDtoMapper.toDomain(order))
                .thenReturn(orderDomain);

        doNothing()
                .when(processOrderPaymentUseCase)
                .process(orderDomain);

        pendingPaymentOrderConsumer.pendingPaymentConsumer(order);

        verify(orderDtoMapper).toDomain(order);
        verify(processOrderPaymentUseCase).process(orderDomain);
    }
}
