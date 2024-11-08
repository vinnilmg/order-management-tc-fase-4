package com.fiap.techchallenge4.order.application.usecases.order;

import com.fiap.techchallenge4.order.application.gateways.order.FinishOrderGateway;
import com.fiap.techchallenge4.order.application.usecases.order.impl.FinishOrderUseCaseImpl;
import com.fiap.techchallenge4.order.domain.enums.OrderStatusEnum;
import com.fiap.techchallenge4.order.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.order.helper.fixture.domain.OrderDomainFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FinishOrderUseCaseTest {
    @InjectMocks
    private FinishOrderUseCaseImpl finishOrderUseCase;
    @Mock
    private FindOrderByIdUseCase findOrderByIdUseCase;
    @Mock
    private FinishOrderGateway finishOrderGateway;

    @Test
    void shouldFinishOrder() {
        final var orderId = 1L;
        final var order = OrderDomainFixture.EM_ROTA_DE_ENTREGA();

        when(findOrderByIdUseCase.find(orderId))
                .thenReturn(order);

        doNothing()
                .when(finishOrderGateway)
                .finish(order);

        assertThat(order.getStatusEnum())
                .isEqualTo(OrderStatusEnum.EM_ROTA_DE_ENTREGA);

        assertThat(order.getCompletionDate())
                .isNull();

        finishOrderUseCase.finish(orderId);

        assertThat(order.getStatusEnum())
                .isEqualTo(OrderStatusEnum.FINALIZADO);

        assertThat(order.getCompletionDate())
                .isNotNull();

        verify(findOrderByIdUseCase).find(orderId);
        verify(finishOrderGateway).finish(order);
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenOrderStatusIsDifferentFromEM_ROTA_DE_ENTREGA() {
        final var orderId = 1L;
        final var order = OrderDomainFixture.CRIADO();

        when(findOrderByIdUseCase.find(orderId))
                .thenReturn(order);

        assertThatThrownBy(() -> finishOrderUseCase.finish(orderId))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Order is in invalid status to finish");

        verify(findOrderByIdUseCase).find(orderId);
        verifyNoInteractions(finishOrderGateway);
    }
}
