package com.fiap.techchallenge4.ms_customer.application.usecases.customer;

import com.fiap.techchallenge4.ms_customer.application.gateways.PaymentGateway;
import com.fiap.techchallenge4.ms_customer.application.usecases.impl.FindPaymentByCustomerCpfUseCaseImpl;
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
class FindPaymentByCustomerCpfUseCaseTest {
    @Mock
    private PaymentGateway paymentGateway;

    @InjectMocks
    private FindPaymentByCustomerCpfUseCaseImpl findPaymentByCustomerCpfUseCase;

    @Test
    void shouldFindPaymentByCustomerId() {
        final var cpf = CustomerConstants.DEFAULT_CPF;
        final var payment = CustomerConstants.DEFAULT_PAYMENT_DOMAIN;

        when(paymentGateway.findPaymentByCustomerCpf(cpf))
                .thenReturn(Optional.of(payment));

        final var result = findPaymentByCustomerCpfUseCase.find(cpf);

        assertThat(result)
                .isNotNull()
                .isEqualTo(payment);

        verify(paymentGateway).findPaymentByCustomerCpf(cpf);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenCustomerPaymentIsNotFound() {
        final var cpf = CustomerConstants.DEFAULT_CPF;

        when(paymentGateway.findPaymentByCustomerCpf(cpf))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> findPaymentByCustomerCpfUseCase.find(cpf))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Customer Payment not found");

        verify(paymentGateway).findPaymentByCustomerCpf(cpf);
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenCpfIsInvalid() {
        final var cpf = "12345";

        assertThatThrownBy(() -> findPaymentByCustomerCpfUseCase.find(cpf))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Customer CPF invalid");

        verifyNoInteractions(paymentGateway);
    }

}
