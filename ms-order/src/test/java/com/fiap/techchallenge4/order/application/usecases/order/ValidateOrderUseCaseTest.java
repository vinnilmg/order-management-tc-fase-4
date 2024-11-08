package com.fiap.techchallenge4.order.application.usecases.order;

import com.fiap.techchallenge4.order.application.gateways.order.UpdateOrderStatusGateway;
import com.fiap.techchallenge4.order.application.usecases.order.impl.ValidateOrderUseCaseImpl;
import com.fiap.techchallenge4.order.domain.enums.OrderStatusEnum;
import com.fiap.techchallenge4.order.domain.validator.OrderDomainValidate;
import com.fiap.techchallenge4.order.helper.fixture.domain.OrderDomainFixture;
import com.fiap.techchallenge4.order.infra.gateways.order.CreateOrderPendingPaymentKafkaRepositoryGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class ValidateOrderUseCaseTest {
    @InjectMocks
    private ValidateOrderUseCaseImpl validateOrderUseCase;
    @Mock
    private UpdateOrderStatusGateway updateOrderStatusGateway;
    @Mock
    private CreateOrderPendingPaymentKafkaRepositoryGateway createOrderGateway;

    @Test
    void shouldValidateOrder() {
        final var order = OrderDomainFixture.CRIADO();

        try (MockedStatic<OrderDomainValidate> mockValidate = mockStatic(OrderDomainValidate.class)) {
            mockValidate.when(() -> OrderDomainValidate.validate(order))
                    .thenReturn(true);

            when(updateOrderStatusGateway.update(order))
                    .thenAnswer(i -> i.getArgument(0));

            when(createOrderGateway.create(order))
                    .thenAnswer(i -> i.getArgument(0));

            validateOrderUseCase.validate(order);
        }

        assertThat(order.getStatusEnum())
                .isNotNull()
                        .isEqualTo(OrderStatusEnum.PENDENTE_PAGAMENTO);

        verify(updateOrderStatusGateway).update(order);
        verify(createOrderGateway).create(order);
    }
}
