package com.fiap.techchallenge4.ms_customer.application.usecases.customer;

import com.fiap.techchallenge4.ms_customer.application.gateways.AddressGateway;
import com.fiap.techchallenge4.ms_customer.application.usecases.impl.FindAddressByCustomerCpfUseCaseImpl;
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
class FindAddressByCustomerCpfUseCaseTest {
    @Mock
    private AddressGateway addressGateway;

    @InjectMocks
    private FindAddressByCustomerCpfUseCaseImpl findAddressByCustomerCpfUseCase;

    @Test
    void shouldFindAddressByCustomerId() {
        final var cpf = CustomerConstants.DEFAULT_CPF;
        final var address = CustomerConstants.DEFAULT_ADDRESS_DOMAIN;

        when(addressGateway.findAddressByCustomerCpf(cpf))
                .thenReturn(Optional.of(address));

        final var result = findAddressByCustomerCpfUseCase.find(cpf);

        assertThat(result)
                .isNotNull()
                .isEqualTo(address);

        verify(addressGateway).findAddressByCustomerCpf(cpf);
    }

    @Test
    void shouldThrowNotFoundExceptionWhenCustomerAddressIsNotFound() {
        final var cpf = CustomerConstants.DEFAULT_CPF;

        when(addressGateway.findAddressByCustomerCpf(cpf))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> findAddressByCustomerCpfUseCase.find(cpf))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Customer Address not found");

        verify(addressGateway).findAddressByCustomerCpf(cpf);
    }

    @Test
    void shouldThrowCustomValidationExceptionWhenCpfIsInvalid() {
        final var cpf = "123456";

        assertThatThrownBy(() -> findAddressByCustomerCpfUseCase.find(cpf))
                .isInstanceOf(CustomValidationException.class)
                .hasMessageContaining("Customer CPF invalid");

        verifyNoInteractions(addressGateway);
    }
}
