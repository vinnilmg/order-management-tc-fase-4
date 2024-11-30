package com.fiap.techchallenge4.product.application.controller;

import com.fiap.techchallenge4.product.application.controller.impl.FilesResourcesControllerImpl;
import com.fiap.techchallenge4.product.application.controller.impl.ProductControllerImpl;
import com.fiap.techchallenge4.product.application.service.CsvLoaderService;
import com.fiap.techchallenge4.product.application.service.ProductService;
import com.fiap.techchallenge4.product.core.enums.StatusCsv;
import com.fiap.techchallenge4.product.core.model.CsvLoader;
import com.fiap.techchallenge4.product.core.model.Product;
import com.fiap.techchallenge4.product.infrasctructure.config.CustomExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilesResourcesControllerImpl.class)
class FilesResourcesControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CsvLoaderService csvLoaderService;

    @Test
    void shouldReturnListOfPendingFiles() throws Exception {
        // Arrange: Cria a lista simulada de arquivos pendentes
        List<CsvLoader> pendingFiles = Arrays.asList(
                CsvLoader.builder()
                        .id(1L)
                        .fileName("file1.csv")
                        .statusCsv(StatusCsv.PENDING)
                        .build()
                ,
                 CsvLoader.builder()
                         .id(2L)
                         .fileName("file2.csv")
                         .statusCsv(StatusCsv.PENDING)
                         .build()
        );
        when(csvLoaderService.findAllByStatusPending()).thenReturn(pendingFiles);

        // Act & Assert: Faz a requisição GET e valida o retorno
        mockMvc.perform(get("/api/files/pending")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].fileName").value("file1.csv"))
                .andExpect(jsonPath("$[0].statusCsv").value(StatusCsv.PENDING.toString()))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].fileName").value("file2.csv"))
                .andExpect(jsonPath("$[1].statusCsv").value(StatusCsv.PENDING.toString()));

        Mockito.verify(csvLoaderService).findAllByStatusPending();
    }
}

