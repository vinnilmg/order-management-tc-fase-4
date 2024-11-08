package com.fiap.techchallenge4.order.application.usecases.order;

import com.fiap.techchallenge4.order.application.gateways.customer.FindCustomerByCpfGateway;
import com.fiap.techchallenge4.order.application.gateways.order.UpdateOrderStatusGateway;
import com.fiap.techchallenge4.order.application.gateways.shipping.CreateShippingGateway;
import com.fiap.techchallenge4.order.application.usecases.order.impl.ProcessOrderShippingUseCaseImpl;
import com.fiap.techchallenge4.order.domain.enums.OrderStatusEnum;
import com.fiap.techchallenge4.order.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.order.domain.exceptions.NotFoundException;
import com.fiap.techchallenge4.order.helper.fixture.domain.CustomerDomainFixture;
import com.fiap.techchallenge4.order.helper.fixture.domain.OrderDomainFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcessOrderShippingUseCaseTest {
    @InjectMocks
    private ProcessOrderShippingUseCaseImpl processOrderShippingUseCase;
    @Mock
    private FindCustomerByCpfGateway findCustomerByCpfGateway;
    @Mock
    private CreateShippingGateway createShippingGateway;
    @Mock
    private UpdateOrderStatusGateway updateOrderStatusGateway;

    @Test
    void shouldThrowCustomValidationExceptionWhenOrderStatusIsDifferentFromPROCESSADO() {
        final var order = OrderDomainFixture.CRIADO();
        assertThatThrownBy(() -> processOrderShippingUseCase.process(order))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Order is in invalid status to process shipping");
    }

    @Test
    void shouldThrowNotFoundExceptionWhenCustomerIsNotFound() {
        final var order = OrderDomainFixture.PROCESSADO();

        when(findCustomerByCpfGateway.find(order.getCpf()))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> processOrderShippingUseCase.process(order))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Customer not found");

        verify(findCustomerByCpfGateway).find(order.getCpf());
    }

    @Test
    void shouldProcessOrderShipping() {
        final var order = OrderDomainFixture.PROCESSADO();
        final var customer = CustomerDomainFixture.FULL();

        when(findCustomerByCpfGateway.find(order.getCpf()))
                .thenReturn(Optional.of(customer));

        doNothing()
                .when(createShippingGateway)
                .create(order.getId(), customer.getAddress().getPostalCode());

        when(updateOrderStatusGateway.update(order))
                .thenReturn(order);

        processOrderShippingUseCase.process(order);

        assertThat(order.getStatusEnum())
                .isEqualTo(OrderStatusEnum.AGUARDANDO_ENVIO);

        verify(findCustomerByCpfGateway).find(order.getCpf());
        verify(createShippingGateway).create(order.getId(), customer.getAddress().getPostalCode());
        verify(updateOrderStatusGateway).update(order);
    }
}
