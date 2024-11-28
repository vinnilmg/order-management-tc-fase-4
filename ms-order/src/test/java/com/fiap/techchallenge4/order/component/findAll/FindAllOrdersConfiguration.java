package com.fiap.techchallenge4.order.component.findAll;

import com.fiap.techchallenge4.order.application.usecases.order.CreateOrderUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FindOrderByIdUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FindOrdersByCpfUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FinishOrderUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.UpdateOrderShippingInfoUseCase;
import com.fiap.techchallenge4.order.infra.controllers.mappers.OrderRequestMapper;
import com.fiap.techchallenge4.order.infra.persistence.repositories.OrderRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class FindAllOrdersConfiguration {
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private FindOrderByIdUseCase findOrderByIdUseCase;
    @MockBean
    private FindOrdersByCpfUseCase findOrdersByCpfUseCase;
    @MockBean
    private CreateOrderUseCase createOrderUseCase;
    @MockBean
    private OrderRequestMapper orderRequestMapper;
    @MockBean
    private UpdateOrderShippingInfoUseCase updateOrderShippingInfoUseCase;
    @MockBean
    private FinishOrderUseCase finishOrderUseCase;
}
