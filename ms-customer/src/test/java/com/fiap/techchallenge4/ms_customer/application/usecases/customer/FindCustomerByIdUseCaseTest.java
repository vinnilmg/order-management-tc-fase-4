package com.fiap.techchallenge4.ms_customer.application.usecases.customer;

import com.fiap.techchallenge4.ms_customer.application.gateways.CustomerGateway;
import com.fiap.techchallenge4.ms_customer.application.usecases.impl.FindCustomerByIdUseCaseImpl;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.NotFoundException;
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
class FindCustomerByIdUseCaseTest {
    @Mock
    private CustomerGateway customerGateway;

    @InjectMocks
    private FindCustomerByIdUseCaseImpl findCustomerByIdUseCase;

    @Test
    void shouldFindCustomerById() {
        final var customerId = 1L;
        final var customer = CustomerFixture.NEW_CUSTOMER();

        when(customerGateway.findCustomerById(customerId))
                .thenReturn(Optional.of(customer));

        final var result = findCustomerByIdUseCase.find(customerId);

        assertThat(result)
                .isNotNull()
                .isEqualTo(customer);

        verify(customerGateway).findCustomerById(customerId);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenCustomerIsNotFound() {
        final var customerId = 1L;

        when(customerGateway.findCustomerById(customerId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> findCustomerByIdUseCase.find(customerId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Customer not found");

        verify(customerGateway).findCustomerById(customerId);
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenIdIsInvalid() {
        final var customerId = -1L;

        assertThatThrownBy(() -> findCustomerByIdUseCase.find(customerId))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Customer Id cannot be null or negative");

        verifyNoInteractions(customerGateway);
    }
}
