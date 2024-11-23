package com.fiap.techchallenge4.ms_customer.application.usecases.customer;

import com.fiap.techchallenge4.ms_customer.application.gateways.CustomerGateway;
import com.fiap.techchallenge4.ms_customer.application.usecases.impl.DeleteCustomerUseCaseImpl;
import com.fiap.techchallenge4.ms_customer.application.usecases.impl.FindCustomerByIdUseCaseImpl;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.NotFoundException;
import com.fiap.techchallenge4.ms_customer.helper.fixture.CustomerFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCustomerUseCaseTest {
    @Mock
    private CustomerGateway customerGateway;

    @Mock
    private FindCustomerByIdUseCaseImpl findCustomerByIdUseCase;

    @InjectMocks
    private DeleteCustomerUseCaseImpl deleteCustomerUseCase;

    @Test
    void shouldDeleteCustomer() {
        final var customerId = 1L;
        final var customer = CustomerFixture.NEW_CUSTOMER();

        when(findCustomerByIdUseCase.find(customerId))
                .thenReturn(customer);

        deleteCustomerUseCase.execute(customerId);

        verify(findCustomerByIdUseCase).find(customerId);
        verify(customerGateway).delete(customerId);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenCustomerIsNotFound() {
        final var customerId = 1L;

        when(findCustomerByIdUseCase.find(customerId))
                .thenReturn(null);

        assertThatThrownBy(() -> deleteCustomerUseCase.execute(customerId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Customer not found");

        verify(findCustomerByIdUseCase).find(customerId);
        verifyNoInteractions(customerGateway);
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenIdIsInvalid() {
        final var customerId = -1L;

        assertThatThrownBy(() -> deleteCustomerUseCase.execute(customerId))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Customer Id cannot be null or negative");

        verifyNoInteractions(customerGateway);
    }

}
