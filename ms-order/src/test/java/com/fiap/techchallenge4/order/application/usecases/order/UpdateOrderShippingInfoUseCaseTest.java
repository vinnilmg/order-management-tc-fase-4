package com.fiap.techchallenge4.order.application.usecases.order;

import com.fiap.techchallenge4.order.application.gateways.order.UpdateOrderStatusGateway;
import com.fiap.techchallenge4.order.application.usecases.order.impl.UpdateOrderShippingInfoUseCaseImpl;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateOrderShippingInfoUseCaseTest {
    @InjectMocks
    private UpdateOrderShippingInfoUseCaseImpl updateOrderShippingInfoUseCase;
    @Mock
    private FindOrderByIdUseCase findOrderByIdUseCase;
    @Mock
    private UpdateOrderStatusGateway updateOrderStatusGateway;

    @Test
    void shouldUpdateOrderShipping() {
        final var orderId = 1L;
        final var order = OrderDomainFixture.AGUARDANDO_ENVIO();

        when(findOrderByIdUseCase.find(orderId))
                .thenReturn(order);

        when(updateOrderStatusGateway.update(order))
                .thenReturn(order);

        assertThat(order.getStatusEnum())
                .isEqualTo(OrderStatusEnum.AGUARDANDO_ENVIO);

        updateOrderShippingInfoUseCase.update(orderId);

        assertThat(order.getStatusEnum())
                .isEqualTo(OrderStatusEnum.EM_ROTA_DE_ENTREGA);

        verify(findOrderByIdUseCase).find(orderId);
        verify(updateOrderStatusGateway).update(order);
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenOrderStatusIsDifferentFromAGUARDANDO_ENVIO() {
        final var orderId = 1L;
        final var order = OrderDomainFixture.CRIADO();

        when(findOrderByIdUseCase.find(orderId))
                .thenReturn(order);

        assertThatThrownBy(() -> updateOrderShippingInfoUseCase.update(orderId))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Order is in invalid status to update");

        verify(findOrderByIdUseCase).find(orderId);
        verifyNoInteractions(updateOrderStatusGateway);
    }
}
