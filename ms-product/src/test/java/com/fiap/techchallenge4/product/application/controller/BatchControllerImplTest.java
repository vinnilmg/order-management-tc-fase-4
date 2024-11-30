package com.fiap.techchallenge4.product.application.controller;

import com.fiap.techchallenge4.product.application.controller.impl.BatchControllerImpl;
import com.fiap.techchallenge4.product.application.service.ProcessBatchService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BatchControllerImpl.class)
class BatchControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProcessBatchService processBatchService;

    @Test
    void shouldProcessBatchSuccessfully() throws Exception {
        // Arrange: Simula a execução do serviço sem erros
        doNothing().when(processBatchService).execute();

        // Act & Assert: Chama o endpoint e verifica o retorno esperado
        mockMvc.perform(post("/api/batch/process")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Batch finalizado com sucesso."));

        // Verifica que o serviço foi chamado
        Mockito.verify(processBatchService).execute();
    }
}
