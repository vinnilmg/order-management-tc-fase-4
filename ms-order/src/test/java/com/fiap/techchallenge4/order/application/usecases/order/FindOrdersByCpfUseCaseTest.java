package com.fiap.techchallenge4.order.application.usecases.order;

import com.fiap.techchallenge4.order.application.gateways.order.FindOrdersByCpfGateway;
import com.fiap.techchallenge4.order.application.usecases.order.impl.FindOrdersByCpfUseCaseImpl;
import com.fiap.techchallenge4.order.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.order.helper.fixture.domain.OrderDomainFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.fiap.techchallenge4.order.helper.constants.OrderConstants.CPF;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindOrdersByCpfUseCaseTest {
    @InjectMocks
    private FindOrdersByCpfUseCaseImpl findOrdersByCpfUseCase;
    @Mock
    private FindOrdersByCpfGateway findOrdersByCpfGateway;

    @Test
    void shouldFindOrdersByCpf() {
        final var cpf = CPF;
        final var orders = List.of(OrderDomainFixture.CRIADO());

        when(findOrdersByCpfGateway.find(cpf))
                .thenReturn(orders);

        final var result = findOrdersByCpfUseCase.find(cpf);
        assertThat(result)
                .isNotEmpty()
                .hasSize(orders.size());

        verify(findOrdersByCpfGateway).find(cpf);
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenCpfIsInvalid() {
        final var cpf = "1112223334567";

        assertThatThrownBy(() -> findOrdersByCpfUseCase.find(cpf))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Customer CPF is invalid");

        verifyNoInteractions(findOrdersByCpfGateway);
    }
}
