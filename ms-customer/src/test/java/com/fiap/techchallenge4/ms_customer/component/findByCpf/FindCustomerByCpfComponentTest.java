package com.fiap.techchallenge4.ms_customer.component.findByCpf;

import com.fiap.techchallenge4.ms_customer.application.usecases.impl.FindCustomerByCpfUseCaseImpl;
import com.fiap.techchallenge4.ms_customer.helper.fixture.entity.CustomerEntityFixture;
import com.fiap.techchallenge4.ms_customer.infra.controllers.CustomerController;
import com.fiap.techchallenge4.ms_customer.infra.controllers.mappers.AddressResponseMapperImpl;
import com.fiap.techchallenge4.ms_customer.infra.controllers.mappers.CustomerResponseMapperImpl;
import com.fiap.techchallenge4.ms_customer.infra.controllers.mappers.PaymentResponseMapperImpl;
import com.fiap.techchallenge4.ms_customer.infra.gateways.CustomerRepositoryGateway;
import com.fiap.techchallenge4.ms_customer.infra.persistence.mappers.AddressEntityMapperImpl;
import com.fiap.techchallenge4.ms_customer.infra.persistence.mappers.CustomerEntityMapperImpl;
import com.fiap.techchallenge4.ms_customer.infra.persistence.mappers.PaymentEntityMapperImpl;
import com.fiap.techchallenge4.ms_customer.infra.persistence.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static com.fiap.techchallenge4.ms_customer.helper.constants.CustomerConstants.DEFAULT_CPF;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {
        FindCustomerByCpfConfiguration.class,
        CustomerController.class,
        FindCustomerByCpfUseCaseImpl.class,
        CustomerRepositoryGateway.class,
        CustomerEntityMapperImpl.class,
        AddressEntityMapperImpl.class,
        PaymentEntityMapperImpl.class,
        CustomerResponseMapperImpl.class,
        AddressResponseMapperImpl.class,
        PaymentResponseMapperImpl.class
})

@WebMvcTest(controllers = CustomerController.class)
class FindCustomerByCpfComponentTest  extends AbstractJUnit4SpringContextTests {
    private static final String ENDPOINT = "/api/customers/cpf/{cpf}";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerController customerController;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .build();
    }

    @Test
    void shouldFindCustomerByCpf() throws Exception {
        final var cpf = DEFAULT_CPF;

        when(customerRepository.findByCpf(cpf))
                .thenReturn(Optional.of(CustomerEntityFixture.CREATED()));

        perform()
                .andExpect(status().isOk());

        verify(customerRepository).findByCpf(cpf);
    }

    private ResultActions perform() throws Exception {
        return mockMvc.perform(get(ENDPOINT, "35202528051"));
    }
}
