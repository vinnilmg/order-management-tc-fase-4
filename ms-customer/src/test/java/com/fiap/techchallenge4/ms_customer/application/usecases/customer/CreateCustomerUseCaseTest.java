package com.fiap.techchallenge4.ms_customer.application.usecases.customer;

import com.fiap.techchallenge4.ms_customer.application.gateways.CustomerGateway;
import com.fiap.techchallenge4.ms_customer.application.usecases.FindCustomerByCpfUseCase;
import com.fiap.techchallenge4.ms_customer.application.usecases.impl.CreateCustomerUseCaseImpl;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.ms_customer.helper.fixture.CustomerFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCustomerUseCaseTest {
    @Mock
    private CustomerGateway customerGateway;

    @Mock
    private FindCustomerByCpfUseCase findCustomerByCpfUseCase;

    @InjectMocks
    private CreateCustomerUseCaseImpl createCustomerUseCase;

    @Test
    void shouldCreateCustomer() {
        final var customer = CustomerFixture.NEW_CUSTOMER();

        when(customerGateway.findCustomerByCpf(customer.getCpf()))
                .thenReturn(Optional.empty());

        when(customerGateway.create(customer))
                .thenAnswer(i -> i.getArguments()[0]);

        final var result = createCustomerUseCase.execute(customer);

        assertThat(result)
                .isNotNull()
                .isEqualTo(customer);

        verify(customerGateway).findCustomerByCpf(customer.getCpf());
        verify(customerGateway).create(customer);


    }

    @Test
    void shouldThrowExceptionWhenCpfExists() {
        final var customer = CustomerFixture.NEW_CUSTOMER();

        when(customerGateway.findCustomerByCpf(customer.getCpf()))
                .thenReturn(Optional.of(customer));

        assertThatThrownBy(() -> createCustomerUseCase.execute(customer))
                .isInstanceOf(CustomValidationException.class)
                .hasMessage("CPF already exists");

        verify(customerGateway).findCustomerByCpf(customer.getCpf());
        verifyNoMoreInteractions(customerGateway);
    }
}
