package com.fiap.techchallenge4.ms_customer.infra.controllers;

import com.fiap.techchallenge4.ms_customer.application.usecases.*;
import com.fiap.techchallenge4.ms_customer.helper.constants.CustomerConstants;
import com.fiap.techchallenge4.ms_customer.helper.fixture.CustomerFixture;
import com.fiap.techchallenge4.ms_customer.helper.fixture.request.CustomerRequestFixture;
import com.fiap.techchallenge4.ms_customer.infra.controllers.mappers.AddressResponseMapper;
import com.fiap.techchallenge4.ms_customer.infra.controllers.mappers.CustomerRequestMapper;
import com.fiap.techchallenge4.ms_customer.infra.controllers.mappers.CustomerResponseMapper;
import com.fiap.techchallenge4.ms_customer.infra.controllers.mappers.PaymentResponseMapper;
import com.fiap.techchallenge4.ms_customer.infra.controllers.response.AddressResponse;
import com.fiap.techchallenge4.ms_customer.infra.controllers.response.CustomerResponse;
import com.fiap.techchallenge4.ms_customer.infra.controllers.response.PaymentResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    @InjectMocks
    private CustomerController customerController;
    @Mock
    private FindAllCustomersUseCase findAllCustomersUseCase;
    @Mock
    private FindCustomerByIdUseCase findCustomerByIdUseCase;
    @Mock
    private FindCustomerByCpfUseCase findCustomerByCpfUseCase;
    @Mock
    private FindPaymentByCustomerCpfUseCase findPaymentByCustomerCpfUseCase;
    @Mock
    private FindAddressByCustomerCpfUseCase findAddressByCustomerCpfUseCase;
    @Mock
    private CreateCustomerUseCase createCustomerUseCase;
    @Mock
    private UpdateCustomerUseCase updateCustomerUseCase;
    @Mock
    private DeleteCustomerUseCase deleteCustomerUseCase;
    @Mock
    private CustomerRequestMapper customerRequestMapper;
    @Mock
    private CustomerResponseMapper customerResponseMapper;
    @Mock
    private PaymentResponseMapper paymentResponseMapper;
    @Mock
    private AddressResponseMapper addressResponseMapper;

    @Test
    void shouldFindAllCustomers() {
        final var customers = CustomerFixture.CUSTOMERS();

        when(findAllCustomersUseCase.find())
                .thenReturn(customers);

        when(customerResponseMapper.toResponses(customers))
                .thenReturn(List.of(mock(CustomerResponse.class)));

        final var result = customerController.findAllCustomers();

        assertThat(result.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        verify(findAllCustomersUseCase).find();
        verify(customerResponseMapper).toResponses(customers);
    }

    @Test
    void shouldFindCustomerById() {
        final var customerId = 1L;
        final var customer = CustomerFixture.NEW_CUSTOMER();

        when(findCustomerByIdUseCase.find(customerId))
                .thenReturn(customer);

        when(customerResponseMapper.toResponse(customer))
                .thenReturn(mock(CustomerResponse.class));

        final var result = customerController.findCustomerById(customerId);

        assertThat(result.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        verify(findCustomerByIdUseCase).find(customerId);
        verify(customerResponseMapper).toResponse(customer);
    }

    @Test
    void shouldFindCustomerByCpf() {
        final var cpf = CustomerConstants.DEFAULT_CPF;
        final var customer = CustomerFixture.NEW_CUSTOMER();

        when(findCustomerByCpfUseCase.find(cpf))
                .thenReturn(customer);

        when(customerResponseMapper.toResponse(customer))
                .thenReturn(mock(CustomerResponse.class));

        final var result = customerController.findCustomerByCpf(cpf);

        assertThat(result.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        verify(findCustomerByCpfUseCase).find(cpf);
        verify(customerResponseMapper).toResponse(customer);
    }

    @Test
    void shouldFindPaymentInfoByCustomerCpf() {
        final var cpf = CustomerConstants.DEFAULT_CPF;
        final var payment = CustomerConstants.DEFAULT_PAYMENT_DOMAIN;

        when(findPaymentByCustomerCpfUseCase.find(cpf))
                .thenReturn(payment);

        when(paymentResponseMapper.toResponse(payment))
                .thenReturn(mock(PaymentResponse.class));

        final var result = customerController.findPaymentByCustomerCpf(cpf);

        assertThat(result.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        verify(findPaymentByCustomerCpfUseCase).find(cpf);
        verify(paymentResponseMapper).toResponse(payment);
    }

    @Test
    void shouldFindAddressInfoByCustomerCpf() {
        final var cpf = CustomerConstants.DEFAULT_CPF;
        final var address = CustomerConstants.DEFAULT_ADDRESS_DOMAIN;

        when(findAddressByCustomerCpfUseCase.find(cpf))
                .thenReturn(address);

        when(addressResponseMapper.toResponse(address))
                .thenReturn(mock(AddressResponse.class));

        final var result = customerController.findAddressByCustomerCpf(cpf);

        assertThat(result.getStatusCode())
                .isEqualTo(HttpStatus.OK);

        verify(findAddressByCustomerCpfUseCase).find(cpf);
        verify(addressResponseMapper).toResponse(address);
    }

    @Test
    void shouldCreateCustomer() {
        final var request = CustomerRequestFixture.FULL();
        final var customer = CustomerFixture.NEW_CUSTOMER();

        when(customerRequestMapper.toCustomer(request))
                .thenReturn(customer);

        when(createCustomerUseCase.execute(customer))
                .thenAnswer(i -> i.getArgument(0));

        when(customerResponseMapper.toResponse(customer))
                .thenReturn(mock(CustomerResponse.class));

        final var result = customerController.createCustomer(request);

        assertThat(result.getStatusCode())
                .isEqualTo(HttpStatus.CREATED);

        verify(customerRequestMapper).toCustomer(request);
        verify(createCustomerUseCase).execute(customer);
        verify(customerResponseMapper).toResponse(customer);
    }

    @Test
    void shouldUpdateCustomer() {
        final var customerId = 1L;
        final var request = CustomerRequestFixture.FULL();
        final var customer = CustomerFixture.NEW_CUSTOMER();

        when(customerRequestMapper.toCustomer(request))
                .thenReturn(customer);

        customerController.updateCustomer(customerId, request);

        verify(customerRequestMapper).toCustomer(request);
        verify(updateCustomerUseCase).execute(customerId, customer);
    }

    @Test
    void shouldDeleteCustomer() {
        final var customerId = 1L;

        customerController.deleteCustomer(customerId);

        verify(deleteCustomerUseCase).execute(customerId);
    }
}
