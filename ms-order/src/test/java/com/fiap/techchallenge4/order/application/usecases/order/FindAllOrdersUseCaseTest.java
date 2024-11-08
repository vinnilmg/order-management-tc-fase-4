package com.fiap.techchallenge4.order.application.usecases.order;

import com.fiap.techchallenge4.order.application.gateways.order.FindAllOrdersGateway;
import com.fiap.techchallenge4.order.application.usecases.order.impl.FindAllOrdersUseCaseImpl;
import com.fiap.techchallenge4.order.helper.fixture.domain.OrderDomainFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllOrdersUseCaseTest {
    @InjectMocks
    private FindAllOrdersUseCaseImpl findAllOrdersUseCase;
    @Mock
    private FindAllOrdersGateway findAllOrdersGateway;

    @Test
    void shouldFindAllOrders() {
        final var orders = OrderDomainFixture.ORDERS();

        when(findAllOrdersGateway.find())
                .thenReturn(orders);

        final var result = findAllOrdersUseCase.find();
        assertThat(result)
                .isNotEmpty()
                .hasSize(orders.size());

        verify(findAllOrdersGateway).find();
    }
}
