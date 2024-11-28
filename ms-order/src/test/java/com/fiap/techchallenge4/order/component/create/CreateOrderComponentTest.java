package com.fiap.techchallenge4.order.component.create;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.techchallenge4.order.application.usecases.order.impl.CreateOrderUseCaseImpl;
import com.fiap.techchallenge4.order.config.error.CustomExceptionHandler;
import com.fiap.techchallenge4.order.helper.fixture.entity.OrderEntityFixture;
import com.fiap.techchallenge4.order.helper.fixture.request.OrderRequestFixture;
import com.fiap.techchallenge4.order.helper.fixture.response.ProviderCustomerResponseFixture;
import com.fiap.techchallenge4.order.helper.fixture.response.ProviderProductResponseFixture;
import com.fiap.techchallenge4.order.helper.fixture.response.ProviderShippingResponseFixture;
import com.fiap.techchallenge4.order.infra.client.CustomerClient;
import com.fiap.techchallenge4.order.infra.client.ProductClient;
import com.fiap.techchallenge4.order.infra.client.ShippingClient;
import com.fiap.techchallenge4.order.infra.client.mappers.ProviderAddressResponseMapperImpl;
import com.fiap.techchallenge4.order.infra.client.mappers.ProviderCustomerResponseMapperImpl;
import com.fiap.techchallenge4.order.infra.client.mappers.ProviderProductResponseMapperImpl;
import com.fiap.techchallenge4.order.infra.client.mappers.ProviderShippingResponseMapperImpl;
import com.fiap.techchallenge4.order.infra.controllers.OrderController;
import com.fiap.techchallenge4.order.infra.controllers.mappers.OrderRequestMapperImpl;
import com.fiap.techchallenge4.order.infra.controllers.mappers.OrderResponseMapperImpl;
import com.fiap.techchallenge4.order.infra.controllers.mappers.ProductResponseMapperImpl;
import com.fiap.techchallenge4.order.infra.controllers.mappers.ShippingResponseMapperImpl;
import com.fiap.techchallenge4.order.infra.controllers.request.OrderRequest;
import com.fiap.techchallenge4.order.infra.gateways.customer.CustomerClientGateway;
import com.fiap.techchallenge4.order.infra.gateways.order.CreateOrderKafkaRepositoryGateway;
import com.fiap.techchallenge4.order.infra.gateways.order.OrderRepositoryGateway;
import com.fiap.techchallenge4.order.infra.gateways.product.ProductClientGateway;
import com.fiap.techchallenge4.order.infra.gateways.shipping.ShippingClientGateway;
import com.fiap.techchallenge4.order.infra.messaging.producer.CreatedOrderProducer;
import com.fiap.techchallenge4.order.infra.persistence.mappers.OrderEntityMapperImpl;
import com.fiap.techchallenge4.order.infra.persistence.mappers.ProductEntityMapperImpl;
import com.fiap.techchallenge4.order.infra.persistence.mappers.ShippingEntityMapperImpl;
import com.fiap.techchallenge4.order.infra.persistence.repositories.OrderRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {
        CreateOrderConfiguration.class,
        OrderController.class,
        CreateOrderUseCaseImpl.class,
        OrderRepositoryGateway.class,
        ProductClientGateway.class,
        CreateOrderKafkaRepositoryGateway.class,
        CustomerClientGateway.class,
        ShippingClientGateway.class,
        OrderEntityMapperImpl.class,
        ProductEntityMapperImpl.class,
        ShippingEntityMapperImpl.class,
        ProviderProductResponseMapperImpl.class,
        CreatedOrderProducer.class,
        ProviderCustomerResponseMapperImpl.class,
        ProviderAddressResponseMapperImpl.class,
        ProviderShippingResponseMapperImpl.class,
        OrderRequestMapperImpl.class,
        OrderResponseMapperImpl.class,
        ProductResponseMapperImpl.class,
        ShippingResponseMapperImpl.class
})

@WebMvcTest(controllers = OrderController.class)
class CreateOrderComponentTest extends AbstractJUnit4SpringContextTests {
    private static final String ENDPOINT = "/api/orders";

    @Autowired
    private MockMvc mockMvc;

    private HttpHeaders headers;

    @Autowired
    private OrderController orderController;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private  CustomerClient customerClient;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private ShippingClient shippingClient;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController)
                .setControllerAdvice(CustomExceptionHandler.class)
                .build();

        headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    void shouldCreateOrder() throws Exception {
        final var request = OrderRequestFixture.FULL();
        final var customer = ProviderCustomerResponseFixture.FULL();

        when(customerClient.getCustomerByCpf(request.cpf()))
                .thenReturn(customer);

        when(productClient.getProductBySku(anyLong()))
                .thenReturn(ProviderProductResponseFixture.WITH_PRICE_TEN());

        doNothing()
                .when(productClient)
                .decreaseStock(anyLong(), any());

        when(shippingClient.simulateShipping(customer.address().postalCode()))
                .thenReturn(ProviderShippingResponseFixture.FULL());

        when(orderRepository.save(any()))
                .thenReturn(OrderEntityFixture.CRIADO());

        perform(request)
                .andExpect(status().isOk());

        verify(customerClient).getCustomerByCpf(request.cpf());
        verify(productClient).getProductBySku(anyLong());
        verify(productClient).decreaseStock(anyLong(), any());
        verify(shippingClient).simulateShipping(customer.address().postalCode());
        verify(orderRepository).save(any());
    }

    private ResultActions perform(final OrderRequest request) throws Exception {
        return mockMvc.perform(post(ENDPOINT)
                .content(toJsonString(request))
                .headers(headers));
    }

    @SneakyThrows
    private static String toJsonString(final Object object) {
        final var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(object);
    }
}
