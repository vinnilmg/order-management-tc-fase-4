package com.fiap.techchallenge4.order.application.usecases.order;

import com.fiap.techchallenge4.order.application.gateways.customer.FindPaymentInfoByCpfGateway;
import com.fiap.techchallenge4.order.application.gateways.order.UpdateOrderStatusGateway;
import com.fiap.techchallenge4.order.application.gateways.payment.ProcessPaymentGateway;
import com.fiap.techchallenge4.order.application.gateways.product.AddStockGateway;
import com.fiap.techchallenge4.order.application.usecases.order.impl.ProcessOrderPaymentUseCaseImpl;
import com.fiap.techchallenge4.order.domain.enums.OrderStatusEnum;
import com.fiap.techchallenge4.order.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.order.domain.exceptions.NotFoundException;
import com.fiap.techchallenge4.order.helper.fixture.domain.OrderDomainFixture;
import com.fiap.techchallenge4.order.helper.fixture.domain.PaymentDomainFixture;
import com.fiap.techchallenge4.order.infra.gateways.order.CreateProcessedOrderKafkaRepositoryGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcessOrderPaymentUseCaseTest {
    @InjectMocks
    private ProcessOrderPaymentUseCaseImpl processOrderPaymentUseCase;
    @Mock
    private FindPaymentInfoByCpfGateway findPaymentInfoByCpfGateway;
    @Mock
    private UpdateOrderStatusGateway updateOrderStatusGateway;
    @Mock
    private CreateProcessedOrderKafkaRepositoryGateway createProcessedOrderKafkaGateway;
    @Mock
    private ProcessPaymentGateway processPaymentGateway;
    @Mock
    private AddStockGateway addStockGateway;

    @Test
    void shouldThrowCustomValidationExceptionWhenOrderStatusIsDifferentFromPENDENTE_PAGAMENTO() {
        final var order = OrderDomainFixture.CRIADO();
        assertThatThrownBy(() -> processOrderPaymentUseCase.process(order))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Order is in invalid status to process payment");
    }

    @Test
    void shouldThrowNotFoundExceptionWhenPaymentInfoIsNotFound() {
        final var order = OrderDomainFixture.PENDENTE_PAGAMENTO();

        when(findPaymentInfoByCpfGateway.find(order.getCpf()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> processOrderPaymentUseCase.process(order))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Payment not found");

        verify(findPaymentInfoByCpfGateway).find(order.getCpf());
    }

    @Test
    void shouldProcessOrderPayment() {
        final var order = OrderDomainFixture.PENDENTE_PAGAMENTO();
        final var payment = PaymentDomainFixture.FULL();

        when(findPaymentInfoByCpfGateway.find(order.getCpf()))
                .thenReturn(Optional.of(payment));

        when(processPaymentGateway.process(payment))
                .thenReturn(true);

        when(updateOrderStatusGateway.update(order))
                .thenReturn(order);

        when(createProcessedOrderKafkaGateway.create(order))
                .thenReturn(order);

        processOrderPaymentUseCase.process(order);

        assertThat(order.getStatusEnum())
                .isEqualTo(OrderStatusEnum.PROCESSADO);

        verify(findPaymentInfoByCpfGateway).find(order.getCpf());
        verify(processPaymentGateway).process(payment);
        verify(updateOrderStatusGateway).update(order);
        verify(createProcessedOrderKafkaGateway).create(order);
    }

    @Test
    void shouldCancelOrderWhenProcessingReturnsFalse() {
        final var order = OrderDomainFixture.PENDENTE_PAGAMENTO();
        final var payment = PaymentDomainFixture.FULL();

        when(findPaymentInfoByCpfGateway.find(order.getCpf()))
                .thenReturn(Optional.of(payment));

        when(processPaymentGateway.process(payment))
                .thenReturn(false);

        doNothing()
                .when(addStockGateway)
                .add(anyLong(), anyInt());

        when(updateOrderStatusGateway.update(order))
                .thenReturn(order);

        processOrderPaymentUseCase.process(order);

        assertThat(order.getStatusEnum())
                .isEqualTo(OrderStatusEnum.CANCELADO);

        verify(findPaymentInfoByCpfGateway).find(order.getCpf());
        verify(processPaymentGateway).process(payment);
        verify(addStockGateway).add(anyLong(), anyInt());
        verify(updateOrderStatusGateway).update(order);
        verifyNoInteractions(createProcessedOrderKafkaGateway);
    }
}
