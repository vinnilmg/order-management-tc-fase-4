package com.fiap.techchallenge4.ms_customer.component.findById;

import com.fiap.techchallenge4.ms_customer.application.usecases.*;
import com.fiap.techchallenge4.ms_customer.infra.controllers.mappers.CustomerRequestMapper;
import com.fiap.techchallenge4.ms_customer.infra.persistence.repositories.CustomerRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

@TestConfiguration
public class FindCustomerByIdConfiguration {
    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private FindAllCustomersUseCase findAllCustomersUseCase;
    @MockBean
    private FindCustomerByCpfUseCase findCustomerByCpfUseCase;
    @MockBean
    private FindAddressByCustomerCpfUseCase findAddressByCustomerCpfUseCase;
    @MockBean
    private FindPaymentByCustomerCpfUseCase findPaymentByCustomerCpfUseCase;
    @MockBean
    private CreateCustomerUseCase createCustomerUseCase;
    @MockBean
    private CustomerRequestMapper customerRequestMapper;
    @MockBean
    private UpdateCustomerUseCase updateCustomerUseCase;
    @MockBean
    private DeleteCustomerUseCase deleteCustomerUseCase;
}
