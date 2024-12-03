package com.fiap.techchallenge4.ms_customer.component.findByCpf;

import com.fiap.techchallenge4.ms_customer.application.usecases.*;
import com.fiap.techchallenge4.ms_customer.infra.controllers.mappers.CustomerRequestMapper;
import com.fiap.techchallenge4.ms_customer.infra.persistence.repositories.CustomerRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class FindCustomerByCpfConfiguration {
    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private FindAllCustomersUseCase findAllCustomersUseCase;
    @MockBean
    private FindCustomerByIdUseCase findCustomerByIdUseCase;
    @MockBean
    private CreateCustomerUseCase createCustomerUseCase;
    @MockBean
    private DeleteCustomerUseCase deleteCustomerUseCase;
    @MockBean
    private FindPaymentByCustomerCpfUseCase findPaymentByCustomerCpfUseCase;
    @MockBean
    private FindAddressByCustomerCpfUseCase findAddressByCustomerCpfUseCase;
    @MockBean
    private UpdateCustomerUseCase updateCustomerUseCase;
    @MockBean
    private CustomerRequestMapper customerRequestMapper;
}
