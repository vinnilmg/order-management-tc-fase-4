package com.fiap.techchallenge4.order.domain.validator;

import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.domain.enums.OrderStatusEnum;
import com.fiap.techchallenge4.order.helper.fixture.domain.OrderDomainFixture;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.fiap.techchallenge4.order.helper.constants.OrderConstants.CPF;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrderDomainValidateTest {

    @Test
    void shouldReturnTrueWhenOrderIsValid() {
        final var order = OrderDomainFixture.CRIADO();
        final var result = OrderDomainValidate.validate(order);
        assertThat(result)
                .isTrue();
    }

    @Test
    void shouldReturnFalseWhenOrderIdIsNull() {
        final var order = mock(Order.class);
        when(order.getId())
                .thenReturn(null);

        final var result = OrderDomainValidate.validate(order);
        assertThat(result)
                .isFalse();
    }

    @Test
    void shouldReturnFalseWhenOrderCpfIsNull() {
        final var order = mock(Order.class);
        when(order.getId())
                .thenReturn(1L);

        when(order.getCpf())
                .thenReturn(null);

        final var result = OrderDomainValidate.validate(order);
        assertThat(result)
                .isFalse();
    }

    @Test
    void shouldReturnFalseWhenOrderStatusIsNull() {
        final var order = mock(Order.class);
        when(order.getId())
                .thenReturn(1L);

        when(order.getCpf())
                .thenReturn(CPF);

        when(order.getStatusEnum())
                .thenReturn(null);

        final var result = OrderDomainValidate.validate(order);
        assertThat(result)
                .isFalse();
    }

    @Test
    void shouldReturnFalseWhenOrderCreationDateIsNull() {
        final var order = mock(Order.class);
        when(order.getId())
                .thenReturn(1L);

        when(order.getCpf())
                .thenReturn(CPF);

        when(order.getStatusEnum())
                .thenReturn(OrderStatusEnum.CRIADO);

        when(order.getCreationDate())
                .thenReturn(null);

        final var result = OrderDomainValidate.validate(order);
        assertThat(result)
                .isFalse();
    }

    @Test
    void shouldReturnFalseWhenOrderTotalIsNull() {
        final var order = mock(Order.class);
        when(order.getId())
                .thenReturn(1L);

        when(order.getCpf())
                .thenReturn(CPF);

        when(order.getStatusEnum())
                .thenReturn(OrderStatusEnum.CRIADO);

        when(order.getCreationDate())
                .thenReturn(LocalDateTime.now());

        when(order.getTotal())
                .thenReturn(null);

        final var result = OrderDomainValidate.validate(order);
        assertThat(result)
                .isFalse();
    }

    @Test
    void shouldReturnFalseWhenOrderTotalWithShippingIsNull() {
        final var order = mock(Order.class);
        when(order.getId())
                .thenReturn(1L);

        when(order.getCpf())
                .thenReturn(CPF);

        when(order.getStatusEnum())
                .thenReturn(OrderStatusEnum.CRIADO);

        when(order.getCreationDate())
                .thenReturn(LocalDateTime.now());

        when(order.getTotal())
                .thenReturn(new BigDecimal(50));

        when(order.getTotalWithShipping())
                .thenReturn(null);

        final var result = OrderDomainValidate.validate(order);
        assertThat(result)
                .isFalse();
    }
}