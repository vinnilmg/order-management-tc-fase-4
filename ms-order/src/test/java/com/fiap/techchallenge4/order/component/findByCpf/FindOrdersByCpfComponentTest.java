package com.fiap.techchallenge4.order.component.findByCpf;

import com.fiap.techchallenge4.order.application.usecases.order.impl.FindOrdersByCpfUseCaseImpl;
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

import static com.fiap.techchallenge4.order.helper.constants.OrderConstants.CPF;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {
        FindOrdersByCpfConfiguration.class,
        OrderController.class,
        FindOrdersByCpfUseCaseImpl.class,
        OrderRepositoryGateway.class,
        OrderEntityMapperImpl.class,
        ProductEntityMapperImpl.class,
        ShippingEntityMapperImpl.class,
        OrderResponseMapperImpl.class,
        ProductResponseMapperImpl.class,
        ShippingResponseMapperImpl.class
})

@WebMvcTest(controllers = OrderController.class)
class FindOrdersByCpfComponentTest extends AbstractJUnit4SpringContextTests {
    private static final String ENDPOINT = "/api/orders/customers/{cpf}";

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
    void shouldFindOrdersByCpf() throws Exception {
        final var cpf = CPF;

        when(orderRepository.findByCpf(cpf))
                .thenReturn(List.of(OrderEntityFixture.CRIADO()));

        perform(cpf)
                .andExpect(status().isOk());

        verify(orderRepository).findByCpf(cpf);
    }

    private ResultActions perform(final String cpf) throws Exception {
        return mockMvc.perform(get(ENDPOINT, cpf));
    }
}
