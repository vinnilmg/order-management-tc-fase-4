package com.fiap.techchallenge4.order.infra.controllers.mappers;

import com.fiap.techchallenge4.order.helper.fixture.domain.OrderDomainFixture;
import com.fiap.techchallenge4.order.infra.controllers.response.OrderResponse;
import com.fiap.techchallenge4.order.infra.controllers.response.ProductResponse;
import com.fiap.techchallenge4.order.infra.controllers.response.ShippingResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderResponseMapperTest {
    private OrderResponseMapper orderResponseMapper;
    private ProductResponseMapper productResponseMapper;
    private ShippingResponseMapper shippingResponseMapper;

    @BeforeEach
    void setup() {
        productResponseMapper = mock(ProductResponseMapper.class);
        shippingResponseMapper = mock(ShippingResponseMapper.class);
        orderResponseMapper = new OrderResponseMapperImpl();
        ReflectionTestUtils.setField(orderResponseMapper, "productResponseMapper", productResponseMapper);
        ReflectionTestUtils.setField(orderResponseMapper, "shippingResponseMapper", shippingResponseMapper);
    }

    @Test
    void shouldMapOrderResponse() {
        final var order = OrderDomainFixture.CRIADO();
        final var products = List.of(mock(ProductResponse.class));
        final var shipping = mock(ShippingResponse.class);

        when(productResponseMapper.toResponses(order.getProducts()))
                .thenReturn(products);

        when(shippingResponseMapper.toResponse(order.getShipping()))
                .thenReturn(shipping);

        final var result = orderResponseMapper.toResponse(order);
        assertThat(result)
                .isNotNull()
                .extracting(
                        OrderResponse::id,
                        OrderResponse::cpf,
                        OrderResponse::status,
                        OrderResponse::creationDate,
                        OrderResponse::completionDate,
                        OrderResponse::subtotal,
                        OrderResponse::total,
                        OrderResponse::products,
                        OrderResponse::shipping
                ).containsExactly(
                        order.getId(),
                        order.getMaskedCpf(),
                        order.getStatus(),
                        DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(order.getCreationDate()),
                        order.getCompletionDate(),
                        order.getFormattedTotal(),
                        order.getFormattedTotalWithShipping(),
                        products,
                        shipping
                );

        verify(productResponseMapper).toResponses(order.getProducts());
        verify(shippingResponseMapper).toResponse(order.getShipping());
    }

    @Test
    void shouldMapOrdersResponses() {
        final var order = OrderDomainFixture.CRIADO();
        final var products = List.of(mock(ProductResponse.class));
        final var shipping = mock(ShippingResponse.class);

        when(productResponseMapper.toResponses(order.getProducts()))
                .thenReturn(products);

        when(shippingResponseMapper.toResponse(order.getShipping()))
                .thenReturn(shipping);

        final var result = orderResponseMapper.toResponses(List.of(order));
        assertThat(result)
                .isNotNull()
                .hasSize(1);

        verify(productResponseMapper).toResponses(order.getProducts());
        verify(shippingResponseMapper).toResponse(order.getShipping());
    }
}
