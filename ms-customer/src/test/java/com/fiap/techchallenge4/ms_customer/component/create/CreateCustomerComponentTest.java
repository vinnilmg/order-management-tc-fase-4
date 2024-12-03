package com.fiap.techchallenge4.ms_customer.component.create;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.techchallenge4.ms_customer.application.usecases.impl.CreateCustomerUseCaseImpl;
import com.fiap.techchallenge4.ms_customer.helper.fixture.entity.CustomerEntityFixture;
import com.fiap.techchallenge4.ms_customer.helper.fixture.request.CustomerRequestFixture;
import com.fiap.techchallenge4.ms_customer.infra.controllers.CustomerController;
import com.fiap.techchallenge4.ms_customer.infra.controllers.mappers.AddressResponseMapperImpl;
import com.fiap.techchallenge4.ms_customer.infra.controllers.mappers.CustomerRequestMapperImpl;
import com.fiap.techchallenge4.ms_customer.infra.controllers.mappers.CustomerResponseMapperImpl;
import com.fiap.techchallenge4.ms_customer.infra.controllers.mappers.PaymentResponseMapperImpl;
import com.fiap.techchallenge4.ms_customer.infra.controllers.request.CustomerRequest;
import com.fiap.techchallenge4.ms_customer.infra.gateways.CustomerRepositoryGateway;
import com.fiap.techchallenge4.ms_customer.infra.persistence.mappers.AddressEntityMapperImpl;
import com.fiap.techchallenge4.ms_customer.infra.persistence.mappers.CustomerEntityMapperImpl;
import com.fiap.techchallenge4.ms_customer.infra.persistence.mappers.PaymentEntityMapperImpl;
import com.fiap.techchallenge4.ms_customer.infra.persistence.repositories.CustomerRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {
        CreateCustomerConfiguration.class,
        CustomerController.class,
        CreateCustomerUseCaseImpl.class,
        CustomerRepositoryGateway.class,
        CustomerEntityMapperImpl.class,
        AddressEntityMapperImpl.class,
        PaymentEntityMapperImpl.class,
        CustomerResponseMapperImpl.class,
        AddressResponseMapperImpl.class,
        PaymentResponseMapperImpl.class,
        CustomerRequestMapperImpl.class
})
@WebMvcTest(controllers = CustomerController.class)
class CreateCustomerComponentTest extends AbstractJUnit4SpringContextTests {
    private static final String ENDPOINT = "/api/customers";

    @Autowired
    private MockMvc mockMvc;

    private HttpHeaders headers;

    @Autowired
    private CustomerController customerController;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .build();

        headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    void shouldCreateCustomer() throws Exception {
        final var request = CustomerRequestFixture.FULL();

        when(customerRepository.findByCpf(request.cpf()))
                .thenReturn(Optional.empty());

        when(customerRepository.save(any()))
                .thenReturn(CustomerEntityFixture.CREATED());

        perform(request)
                .andExpect(status().isCreated());

        verify(customerRepository).findByCpf(request.cpf());
        verify(customerRepository).save(any());
    }

    private ResultActions perform(final CustomerRequest request) throws Exception {
        return mockMvc.perform(post(ENDPOINT)
                .content(toJsonString(request))
                .headers(headers));
    }

    @SneakyThrows
    private static String toJsonString(final Object object) {
        final var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(object);
    }
}
