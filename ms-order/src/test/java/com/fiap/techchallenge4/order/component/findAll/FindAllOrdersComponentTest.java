package com.fiap.techchallenge4.order.component.findAll;

import com.fiap.techchallenge4.order.application.usecases.order.impl.FindAllOrdersUseCaseImpl;
import com.fiap.techchallenge4.order.config.error.CustomExceptionHandler;
import com.fiap.techchallenge4.order.helper.fixture.entity.OrderEntityFixture;
import com.fiap.techchallenge4.order.infra.controllers.OrderController;
import com.fiap.techchallenge4.order.infra.controllers.mappers.OrderResponseMapperImpl;
import com.fiap.techchallenge4.order.infra.controllers.mappers.ProductResponseMapperImpl;
import com.fiap.techchallenge4.order.infra.controllers.mappers.ShippingResponseMapperImpl;
import com.fiap.techchallenge4.order.infra.gateways.order.OrderRepositoryGateway;
import com.fiap.techchallenge4.order.infra.persistence.mappers.OrderEntityMapperImpl;
import com.fiap.techchallenge4.order.infra.persistence.mappers.ProductEntityMapperImpl;
import com.fiap.techchallenge4.order.infra.persistence.mappers.ShippingEntityMapperImpl;
import com.fiap.techchallenge4.order.infra.persistence.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {
        FindAllOrdersConfiguration.class,
        OrderController.class,
        FindAllOrdersUseCaseImpl.class,
        OrderRepositoryGateway.class,
        OrderEntityMapperImpl.class,
        ProductEntityMapperImpl.class,
        ShippingEntityMapperImpl.class,
        OrderResponseMapperImpl.class,
        ProductResponseMapperImpl.class,
        ShippingResponseMapperImpl.class
})

@WebMvcTest(controllers = OrderController.class)
class FindAllOrdersComponentTest extends AbstractJUnit4SpringContextTests {
    private static final String ENDPOINT = "/api/orders";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderController orderController;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setControllerAdvice(CustomExceptionHandler.class)
                .build();
    }

    @Test
    void shouldFindAllOrders() throws Exception {
        when(orderRepository.findAll())
                .thenReturn(List.of(OrderEntityFixture.CRIADO()));

        perform()
                .andExpect(status().isOk());

        verify(orderRepository).findAll();
    }

    private ResultActions perform() throws Exception {
        return mockMvc.perform(get(ENDPOINT));
    }
}