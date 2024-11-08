package com.fiap.techchallenge4.order.application.usecases.order;

import com.fiap.techchallenge4.order.application.gateways.order.FindOrderByIdGateway;
import com.fiap.techchallenge4.order.application.usecases.order.impl.FindOrderByIdUseCaseImpl;
import com.fiap.techchallenge4.order.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.order.domain.exceptions.NotFoundException;
import com.fiap.techchallenge4.order.helper.fixture.domain.OrderDomainFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindOrderByIdUseCaseTest {
    @InjectMocks
    private FindOrderByIdUseCaseImpl findOrderByIdUseCase;
    @Mock
    private FindOrderByIdGateway findOrderByIdGateway;

    @Test
    void shouldFindOrderById() {
        final var orderId = 1L;
        final var order = OrderDomainFixture.CRIADO();

        when(findOrderByIdGateway.find(orderId))
                .thenReturn(Optional.of(order));

        final var result = findOrderByIdUseCase.find(orderId);
        assertThat(result)
                .isNotNull()
                .isEqualTo(order);

        verify(findOrderByIdGateway).find(orderId);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenOrderIsNotFound() {
        final var orderId = 1L;

        when(findOrderByIdGateway.find(orderId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> findOrderByIdUseCase.find(orderId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Order not found");

        verify(findOrderByIdGateway).find(orderId);
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenIdIsInvalid() {
        final var orderId = -1L;

        assertThatThrownBy(() -> findOrderByIdUseCase.find(orderId))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Order Id cannot be null or negative");

        verifyNoInteractions(findOrderByIdGateway);
    }
}
