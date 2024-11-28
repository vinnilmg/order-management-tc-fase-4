package com.fiap.techchallenge4.payment.component.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.techchallenge4.payment.controller.PaymentProcessorController;
import com.fiap.techchallenge4.payment.controller.request.PaymentDataRequest;
import com.fiap.techchallenge4.payment.helper.fixture.request.PaymentDataRequestFixture;
import com.fiap.techchallenge4.payment.service.PaymentProcessorServiceImpl;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {
        ProcessPaymentConfiguration.class,
        PaymentProcessorController.class,
        PaymentProcessorServiceImpl.class
})

@WebMvcTest(controllers = PaymentProcessorController.class)
class ProcessPaymentComponentTest extends AbstractJUnit4SpringContextTests {
    private static final String ENDPOINT = "/api/payment/processor";
    private static final String JSON_PATH_APPROVED = "$.approved";

    @Autowired
    private MockMvc mockMvc;

    private HttpHeaders headers;

    @BeforeEach
    void setup() {
        headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

    @Test
    void shouldApprovePayment() throws Exception {
        final var request = PaymentDataRequestFixture.VALID();

        perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_PATH_APPROVED).value(true));
    }

    @Test
    void shouldNotApprovePayment() throws Exception {
        final var request = PaymentDataRequestFixture.UNAPPROVED();

        perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath(JSON_PATH_APPROVED).value(false));
    }

    private ResultActions perform(final PaymentDataRequest request) throws Exception {
        return mockMvc.perform(post(ENDPOINT)
                .content(toJsonString(request))
                .headers(headers));
    }

    private static String toJsonString(final Object object) throws JsonProcessingException {
        final var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(object);
    }
}
