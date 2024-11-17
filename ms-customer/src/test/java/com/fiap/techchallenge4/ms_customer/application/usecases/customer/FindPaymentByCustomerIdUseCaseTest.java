package com.fiap.techchallenge4.ms_customer.application.usecases.customer;

import com.fiap.techchallenge4.ms_customer.application.gateways.PaymentGateway;
import com.fiap.techchallenge4.ms_customer.application.usecases.impl.FindPaymentByCustomerIdUseCaseImpl;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.CustomValidationException;
import com.fiap.techchallenge4.ms_customer.domain.exceptions.NotFoundException;
import com.fiap.techchallenge4.ms_customer.helper.constants.CustomerConstants;
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
class FindPaymentByCustomerIdUseCaseTest {
    @Mock
    private PaymentGateway paymentGateway;

    @InjectMocks
    private FindPaymentByCustomerIdUseCaseImpl findPaymentByCustomerIdUseCase;

    @Test
    void shouldFindPaymentByCustomerId() {
        final var customerId = 1L;
        final var payment = CustomerConstants.DEFAULT_PAYMENT_DOMAIN;

        when(paymentGateway.findPaymentByCustomerId(customerId))
                .thenReturn(Optional.of(payment));

        final var result = findPaymentByCustomerIdUseCase.find(customerId);

        assertThat(result)
                .isNotNull()
                .isEqualTo(payment);

        verify(paymentGateway).findPaymentByCustomerId(customerId);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenCustomerPaymentIsNotFound() {
        final var customerId = 1L;

        when(paymentGateway.findPaymentByCustomerId(customerId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> findPaymentByCustomerIdUseCase.find(customerId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Customer Payment not found");

        verify(paymentGateway).findPaymentByCustomerId(customerId);
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenIdIsInvalid() {
        final var customerId = -1L;

        assertThatThrownBy(() -> findPaymentByCustomerIdUseCase.find(customerId))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Customer Id cannot be null or negative");

        verifyNoInteractions(paymentGateway);
    }

}
