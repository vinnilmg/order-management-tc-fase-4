package com.fiap.techchallenge4.ms_shipping.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.techchallenge4.ms_shipping.controller.request.CourierUpdateRequest;
import com.fiap.techchallenge4.ms_shipping.model.CourierEntity;
import com.fiap.techchallenge4.ms_shipping.model.DeliveryEntity;
import com.fiap.techchallenge4.ms_shipping.model.ShippingEntity;
import com.fiap.techchallenge4.ms_shipping.model.enums.AvaialableCourierEnum;
import com.fiap.techchallenge4.ms_shipping.model.enums.DeliveryStatusEnum;
import com.fiap.techchallenge4.ms_shipping.model.enums.RegionEnum;
import com.fiap.techchallenge4.ms_shipping.service.CourierService;
import com.fiap.techchallenge4.ms_shipping.service.DeliveryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CourierController.class)
class CourierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CourierService courierService;

    @MockBean
    private DeliveryService deliveryService;

    @Test
    @DisplayName("Deve retornar todos os couriers")
    void findAll() throws Exception {
        // Arrange
        ShippingEntity shipping = new ShippingEntity();
        shipping.setRegion(RegionEnum.SUL);
        shipping.setCepStart("12345678");
        shipping.setCepEnd("87654321");
        shipping.setDaysToDelivery("3");
        shipping.setDeliveryPrice(new BigDecimal("20.00"));

        CourierEntity courier = new CourierEntity();
        courier.setId(1L);
        courier.setName("Courier 1");
        courier.setStatus(AvaialableCourierEnum.AVAILABLE);
        courier.setPhone("123-456-789");
        courier.setRegion(shipping); // Relacionamento com ShippingEntity

        Mockito.when(courierService.findAll()).thenReturn(List.of(courier));

        // Act & Assert
        mockMvc.perform(get("/api/courier"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Courier 1"))
                .andExpect(jsonPath("$[0].region.region").value("SUL"));
    }

    @Test
    @DisplayName("Deve retornar um courier por ID")
    void findById() throws Exception {
        // Arrange
        ShippingEntity shipping = new ShippingEntity();
        shipping.setRegion(RegionEnum.SUL);
        shipping.setCepStart("12345678");
        shipping.setCepEnd("87654321");
        shipping.setDaysToDelivery("3");
        shipping.setDeliveryPrice(new BigDecimal("20.00"));

        CourierEntity courier = new CourierEntity();
        courier.setId(1L);
        courier.setName("Courier 1");
        courier.setStatus(AvaialableCourierEnum.AVAILABLE);
        courier.setPhone("123-456-789");
        courier.setRegion(shipping);

        Mockito.when(courierService.findById(1L)).thenReturn(Optional.of(courier));

        // Act & Assert
        mockMvc.perform(get("/api/courier/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Courier 1"))
                .andExpect(jsonPath("$.region.region").value("SUL"));
    }

    @Test
    @DisplayName("Deve criar um novo courier")
    void save() throws Exception {
        // Arrange
        ShippingEntity shipping = new ShippingEntity();
        shipping.setRegion(RegionEnum.SUL);
        shipping.setCepStart("12345678");
        shipping.setCepEnd("87654321");
        shipping.setDaysToDelivery("3");
        shipping.setDeliveryPrice(new BigDecimal("20.00"));

        CourierEntity courier = new CourierEntity();
        courier.setId(1L);
        courier.setName("Courier 1");
        courier.setStatus(AvaialableCourierEnum.AVAILABLE);
        courier.setPhone("123-456-789");
        courier.setRegion(shipping);

        Mockito.when(courierService.save(any(CourierEntity.class))).thenReturn(courier);

        // Act & Assert
        mockMvc.perform(post("/api/courier")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(courier)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Courier 1"))
                .andExpect(jsonPath("$.region.region").value("SUL"));
    }

    @Test
    @DisplayName("Deve atualizar o status de um courier")
    void updateStatus() throws Exception {
        // Arrange
        ShippingEntity shipping = new ShippingEntity();
        shipping.setRegion(RegionEnum.SUL);
        shipping.setCepStart("12345678");
        shipping.setCepEnd("87654321");
        shipping.setDaysToDelivery("3");
        shipping.setDeliveryPrice(new BigDecimal("20.00"));

        CourierEntity updatedCourier = new CourierEntity();
        updatedCourier.setId(1L);
        updatedCourier.setName("Courier 1");
        updatedCourier.setStatus(AvaialableCourierEnum.UNAVAILABLE);  // Status atualizado
        updatedCourier.setPhone("123-456-789");
        updatedCourier.setRegion(shipping);

        // Criação do request para atualizar o status
        CourierUpdateRequest updateRequest = new CourierUpdateRequest(AvaialableCourierEnum.UNAVAILABLE);  // Usando o construtor

        // Simulação da chamada do serviço para retornar o courier com o status atualizado
        Mockito.when(courierService.updateStatus(eq(1L), any(CourierUpdateRequest.class)))
                .thenReturn(updatedCourier);

        // Act & Assert
        mockMvc.perform(put("/api/courier/updatestatus/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))  // Enviando o request corretamente
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UNAVAILABLE"));  // Verificando o status no retorno
    }

    @Test
    @DisplayName("Deve retornar entregas associadas a um courier")
    void findByCourier() throws Exception {
        // Arrange
        DeliveryEntity delivery = new DeliveryEntity();
        delivery.setId(1L);
        delivery.setOrderId(123L);
        delivery.setStatus(DeliveryStatusEnum.WAITING_FOR_DELIVERY);
        delivery.setLatitude(0);
        delivery.setLongitude(0);
        delivery.setCourier(new CourierEntity());
        delivery.setRegion(new ShippingEntity());

        Mockito.when(deliveryService.findByCourier(1L)).thenReturn(Optional.of(List.of(delivery)));

        // Act & Assert
        mockMvc.perform(get("/api/delivery-courier/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }
}
