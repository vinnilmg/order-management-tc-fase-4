package com.fiap.techchallenge4.order.component.finish;

import com.fiap.techchallenge4.order.application.usecases.order.impl.FindOrderByIdUseCaseImpl;
import com.fiap.techchallenge4.order.application.usecases.order.impl.FinishOrderUseCaseImpl;
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

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {
        FinishOrderConfiguration.class,
        OrderController.class,
        FinishOrderUseCaseImpl.class,
        OrderRepositoryGateway.class,
        OrderEntityMapperImpl.class,
        ProductEntityMapperImpl.class,
        ShippingEntityMapperImpl.class,
        OrderResponseMapperImpl.class,
        ProductResponseMapperImpl.class,
        ShippingResponseMapperImpl.class,
        FindOrderByIdUseCaseImpl.class
})

@WebMvcTest(controllers = OrderController.class)
class FinishOrderComponentTest extends AbstractJUnit4SpringContextTests {
    private static final String ENDPOINT = "/api/orders/{id}/finish";
    private static final Long ORDER_ID = 1L;

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
    void shouldFinishOrder() throws Exception {
        final var id = ORDER_ID;

        when(orderRepository.findById(id))
                .thenReturn(Optional.of(OrderEntityFixture.EM_ROTA_DE_ENTREGA()));

        doNothing()
                .when(orderRepository)
                .updateStatusAndCompletionDate(eq(id), anyString(), any());

        perform(id)
                .andExpect(status().isNoContent());

        verify(orderRepository).findById(id);
        verify(orderRepository).updateStatusAndCompletionDate(eq(id), anyString(), any());
    }

    @Test
    void shouldNotFinishOrder() throws Exception {
        final var id = ORDER_ID;

        when(orderRepository.findById(id))
                .thenReturn(Optional.of(OrderEntityFixture.CRIADO()));

        perform(id)
                .andExpect(status().isBadRequest());

        verify(orderRepository).findById(id);
    }

    private ResultActions perform(final Long id) throws Exception {
        return mockMvc.perform(put(ENDPOINT, id));
    }
}
