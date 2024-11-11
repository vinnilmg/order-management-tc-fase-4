package com.fiap.techchallenge4.ms_customer.application.usecases.customer;

import com.fiap.techchallenge4.ms_customer.application.gateways.CustomerGateway;
import com.fiap.techchallenge4.ms_customer.application.usecases.impl.FindCustomerByCpfUseCaseImpl;
import com.fiap.techchallenge4.ms_customer.application.usecases.impl.FindCustomerByIdUseCaseImpl;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.ms_customer.helper.fixture.CustomerFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.fiap.techchallenge4.ms_customer.helper.constants.CustomerConstants.DEFAULT_CPF;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindCustomerByCpfUseCaseTest {
    @Mock
    private CustomerGateway customerGateway;

    @InjectMocks
    private FindCustomerByCpfUseCaseImpl findCustomerByCpfUseCase;

    @Test
    void shouldFindCustomerByCpf() {
        final var cpf = DEFAULT_CPF;
        final var customer = CustomerFixture.NEW_CUSTOMER();

        when(customerGateway.findCustomerByCpf(cpf))
                .thenReturn(Optional.of(customer));

        final var result = findCustomerByCpfUseCase.find(cpf);

        assertThat(result)
                .isNotNull()
                .isEqualTo(customer);

        verify(customerGateway).findCustomerByCpf(cpf);
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenCpfIsInvalid() {
        final var cpf = "11122233345678";

        assertThatThrownBy(() -> findCustomerByCpfUseCase.find(cpf))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Customer CPF invalid");

        verifyNoInteractions(customerGateway);
    }
}
