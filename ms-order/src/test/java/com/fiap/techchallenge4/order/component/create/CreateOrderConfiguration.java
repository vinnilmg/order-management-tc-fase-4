package com.fiap.techchallenge4.order.component.create;

import com.fiap.techchallenge4.order.application.usecases.order.FindAllOrdersUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FindOrderByIdUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FindOrdersByCpfUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FinishOrderUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.UpdateOrderShippingInfoUseCase;
import com.fiap.techchallenge4.order.infra.client.CustomerClient;
import com.fiap.techchallenge4.order.infra.client.ProductClient;
import com.fiap.techchallenge4.order.infra.client.ShippingClient;
import com.fiap.techchallenge4.order.infra.persistence.repositories.OrderRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;

@TestConfiguration
public class CreateOrderConfiguration {
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private FindAllOrdersUseCase findAllOrdersUseCase;
    @MockBean
    private FindOrdersByCpfUseCase findOrdersByCpfUseCase;
    @MockBean
    private FindOrderByIdUseCase findOrderByIdUseCase;
    @MockBean
    private UpdateOrderShippingInfoUseCase updateOrderShippingInfoUseCase;
    @MockBean
    private FinishOrderUseCase finishOrderUseCase;
    @MockBean
    private ProductClient productClient;
    @MockBean
    private KafkaTemplate kafkaTemplate;
    @MockBean
    private CustomerClient customerClient;
    @MockBean
    private ShippingClient shippingClient;
}
