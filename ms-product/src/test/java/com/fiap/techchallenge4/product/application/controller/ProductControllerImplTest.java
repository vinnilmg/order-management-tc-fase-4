package com.fiap.techchallenge4.product.application.controller;

import com.fiap.techchallenge4.product.application.controller.impl.ProductControllerImpl;
import com.fiap.techchallenge4.product.application.dto.ProductQuantityDTO;
import com.fiap.techchallenge4.product.application.service.ProductService;
import com.fiap.techchallenge4.product.core.model.Product;
import com.fiap.techchallenge4.product.infrasctructure.config.CustomExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ContextConfiguration(classes = {
        ProductService.class
})
@WebMvcTest(controllers = ProductControllerImpl.class)
class ProductControllerImplTest {

    private static final String BASE_URL = "/api/products";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ProductControllerImpl(productService))
                .setControllerAdvice(CustomExceptionHandler.class)
                .build();
    }

    @Test
    void shouldDecreaseStockSuccessfully() throws Exception {
        // Act & Assert
        mockMvc.perform(put(BASE_URL + "/{skuId}/decrease-stock", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"quantity\": 10 }"))
                .andExpect(status().isNoContent());

        verify(productService).decreaseStock(eq(1L), eq(10));
    }

    @Test
    void shouldAddStockSuccessfully() throws Exception {
        // Act & Assert
        mockMvc.perform(put(BASE_URL + "/{skuId}/additional-stock", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"quantity\": 5 }"))
                .andExpect(status().isNoContent());

        verify(productService).addStock(eq(1L), eq(5));
    }

    @Test
    void shouldFindProductById() throws Exception {
        Product product = Product.builder()
                .id(1L)
                .name("Test Product")
                .stock(100)
                .build();
        when(productService.findById(eq(1L))).thenReturn(product);

        mockMvc.perform(get(BASE_URL + "/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.stock").value(100));

        verify(productService).findById(eq(1L));
    }

    @Test
    void shouldFindProductBySkuId() throws Exception {
        Product product = Product.builder()
                .id(1L)
                .name("Test Product")
                .stock(100)
                .build();
        when(productService.findBySkuId(eq(123L))).thenReturn(product);

        mockMvc.perform(get(BASE_URL + "/skuId/{skuId}", 123L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Product"))
                .andExpect(jsonPath("$.stock").value(100));

        verify(productService).findBySkuId(eq(123L));
    }
}

