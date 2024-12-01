package com.fiap.techchallenge4.ms_customer.application.usecases.customer;

import com.fiap.techchallenge4.ms_customer.application.gateways.CustomerGateway;
import com.fiap.techchallenge4.ms_customer.application.usecases.FindCustomerByIdUseCase;
import com.fiap.techchallenge4.ms_customer.application.usecases.impl.UpdateCustomerUseCaseImpl;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.NotFoundException;
import com.fiap.techchallenge4.ms_customer.helper.fixture.CustomerFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCustomerUseCaseTest {
    @Mock
    private CustomerGateway customerGateway;

    @Mock
    private FindCustomerByIdUseCase findCustomerByIdUseCase;

    @InjectMocks
    private UpdateCustomerUseCaseImpl updateCustomerUseCase;

    @Test
    void shouldUpdateCustomer() {
        final var customerId = 1L;
        final var existingCustomer = CustomerFixture.NEW_CUSTOMER();
        final var updatedCustomer = CustomerFixture.NEW_CUSTOMER();

        when(customerGateway.findCustomerById(customerId))
                .thenReturn(Optional.of(existingCustomer));

        updateCustomerUseCase.execute(customerId, updatedCustomer);

        assertEquals(customerId, updatedCustomer.getId());
        verify(customerGateway).findCustomerById(customerId);
        verify(customerGateway).update(updatedCustomer);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenCustomerIsNotFound() {
        final var customerId = 1L;
        final var customer = CustomerFixture.NEW_CUSTOMER();

        assertThatThrownBy(() -> updateCustomerUseCase.execute(customerId, customer))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Customer not found");

        verify(customerGateway).findCustomerById(customerId);
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenIdIsInvalid() {
        final var customerId = -1L;
        final var customer = CustomerFixture.NEW_CUSTOMER();

        assertThatThrownBy(() -> updateCustomerUseCase.execute(customerId, customer))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Customer Id cannot be null or negative");

        verifyNoInteractions(customerGateway);
    }
}
