package com.fiap.techchallenge4.ms_customer.component.create;

import com.fiap.techchallenge4.ms_customer.application.usecases.*;
import com.fiap.techchallenge4.ms_customer.infra.persistence.repositories.CustomerRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class CreateCustomerConfiguration {
    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private FindAllCustomersUseCase findAllCustomersUseCase;
    @MockBean
    private FindCustomerByCpfUseCase findCustomerByCpfUseCase;
    @MockBean
    private FindCustomerByIdUseCase findCustomerByIdUseCase;
    @MockBean
    private DeleteCustomerUseCase deleteCustomerUseCase;
    @MockBean
    private FindPaymentByCustomerCpfUseCase findPaymentByCustomerCpfUseCase;
    @MockBean
    private FindAddressByCustomerCpfUseCase findAddressByCustomerCpfUseCase;
    @MockBean
    private UpdateCustomerUseCase updateCustomerUseCase;
}
