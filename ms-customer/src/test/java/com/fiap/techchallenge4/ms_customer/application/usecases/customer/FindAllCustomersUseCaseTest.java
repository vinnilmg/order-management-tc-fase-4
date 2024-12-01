package com.fiap.techchallenge4.ms_customer.application.usecases.customer;

import com.fiap.techchallenge4.ms_customer.application.gateways.CustomerGateway;
import com.fiap.techchallenge4.ms_customer.application.usecases.impl.FindAllCustomersUseCaseImpl;
import com.fiap.techchallenge4.ms_customer.helper.fixture.CustomerFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindAllCustomersUseCaseTest {

    @Mock
    private CustomerGateway customerGateway;

    @InjectMocks
    private FindAllCustomersUseCaseImpl findAllCustomersUseCase;

    @Test
    void shouldFindAllCustomers() {
        final var customers = CustomerFixture.CUSTOMERS();

        when(customerGateway.findAllCustomers())
                .thenReturn(customers);

        final var result = findAllCustomersUseCase.find();

        assertThat(result)
                .isNotEmpty()
                .hasSize(customers.size());

        verify(customerGateway).findAllCustomers();
    }
}
