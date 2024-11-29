package com.fiap.techchallenge4.order.component.findByCpf;

import com.fiap.techchallenge4.order.application.usecases.order.CreateOrderUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FindAllOrdersUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FinishOrderUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.UpdateOrderShippingInfoUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.impl.FindOrderByIdUseCaseImpl;
import com.fiap.techchallenge4.order.infra.controllers.mappers.OrderRequestMapper;
import com.fiap.techchallenge4.order.infra.persistence.repositories.OrderRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class FindOrdersByCpfConfiguration {
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private FindAllOrdersUseCase findAllOrdersUseCase;
    @MockBean
    private FindOrderByIdUseCaseImpl findOrderByIdUseCase;
    @MockBean
    private CreateOrderUseCase createOrderUseCase;
    @MockBean
    private OrderRequestMapper orderRequestMapper;
    @MockBean
    private UpdateOrderShippingInfoUseCase updateOrderShippingInfoUseCase;
    @MockBean
    private FinishOrderUseCase finishOrderUseCase;
}
