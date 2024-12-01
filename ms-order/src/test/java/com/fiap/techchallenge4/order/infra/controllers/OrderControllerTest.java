package com.fiap.techchallenge4.order.infra.controllers;

import com.fiap.techchallenge4.order.application.usecases.order.CreateOrderUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FindAllOrdersUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FindOrderByIdUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FindOrdersByCpfUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FinishOrderUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.UpdateOrderShippingInfoUseCase;
import com.fiap.techchallenge4.order.helper.fixture.domain.OrderDomainFixture;
import com.fiap.techchallenge4.order.helper.fixture.request.OrderRequestFixture;
import com.fiap.techchallenge4.order.infra.controllers.mappers.OrderRequestMapper;
import com.fiap.techchallenge4.order.infra.controllers.mappers.OrderResponseMapper;
import com.fiap.techchallenge4.order.infra.controllers.response.OrderResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.fiap.techchallenge4.order.helper.constants.OrderConstants.CPF;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {
    @InjectMocks
    private OrderController orderController;
    @Mock
    private FindAllOrdersUseCase findAllOrdersUseCase;
    @Mock
    private FindOrderByIdUseCase findOrderByIdUseCase;
    @Mock
    private FindOrdersByCpfUseCase findOrdersByCpfUseCase;
    @Mock
    private CreateOrderUseCase createOrderUseCase;
    @Mock
    private OrderResponseMapper orderResponseMapper;
    @Mock
    private OrderRequestMapper orderRequestMapper;
    @Mock
    private UpdateOrderShippingInfoUseCase updateOrderShippingInfoUseCase;
    @Mock
    private FinishOrderUseCase finishOrderUseCase;

    @Test
    void shouldFindAllOrders() {
        final var orders = List.of(OrderDomainFixture.CRIADO());

        when(findAllOrdersUseCase.find())
                .thenReturn(orders);

        when(orderResponseMapper.toResponses(orders))
                .thenReturn(List.of(mock(OrderResponse.class)));

        final var result = orderController.findAllOrders();

        assertThat(result.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        verify(findAllOrdersUseCase).find();
        verify(orderResponseMapper).toResponses(orders);
    }

    @Test
    void shouldFindOrderById() {
        final var orderId = 1L;
        final var order = OrderDomainFixture.CRIADO();

        when(findOrderByIdUseCase.find(orderId))
                .thenReturn(order);

        when(orderResponseMapper.toResponse(order))
                .thenReturn(mock(OrderResponse.class));

        final var result = orderController.findOrderById(orderId);

        assertThat(result.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        verify(findOrderByIdUseCase).find(orderId);
        verify(orderResponseMapper).toResponse(order);
    }

    @Test
    void shouldFindCustomerOrdersByCpf() {
        final var cpf = CPF;
        final var orders = List.of(OrderDomainFixture.CRIADO());

        when(findOrdersByCpfUseCase.find(cpf))
                .thenReturn(orders);

        when(orderResponseMapper.toResponses(orders))
                .thenReturn(List.of(mock(OrderResponse.class)));

        final var result = orderController.findCustomerOrdersByCpf(cpf);

        assertThat(result.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        verify(findOrdersByCpfUseCase).find(cpf);
        verify(orderResponseMapper).toResponses(orders);
    }

    @Test
    void shouldCreateOrder() {
        final var request = OrderRequestFixture.FULL();
        final var order = OrderDomainFixture.CRIADO();

        when(orderRequestMapper.toOrder(request))
                .thenReturn(order);

        when(createOrderUseCase.execute(order))
                .thenAnswer(i -> i.getArgument(0));

        when(orderResponseMapper.toResponse(order))
                .thenReturn(mock(OrderResponse.class));

        final var result = orderController.createOrder(request);

        assertThat(result.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        verify(orderRequestMapper).toOrder(request);
        verify(createOrderUseCase).execute(order);
        verify(orderResponseMapper).toResponse(order);
    }

    @Test
    void shouldUpdateOrderToDeliveryRoute() {
        final var orderId = 1L;

        doNothing()
                .when(updateOrderShippingInfoUseCase)
                .update(orderId);

        orderController.updateOrderToDeliveryRoute(orderId);

        verify(updateOrderShippingInfoUseCase).update(orderId);
    }

    @Test
    void shouldFinishOrder() {
        final var orderId = 1L;

        doNothing()
                .when(finishOrderUseCase)
                .finish(orderId);

        orderController.finishOrder(orderId);

        verify(finishOrderUseCase).finish(orderId);
    }
}
