package com.fiap.techchallenge4.ms_shipping.controller;

import com.fiap.techchallenge4.ms_shipping.dto.DeliveryDto;
import com.fiap.techchallenge4.ms_shipping.dto.request.DeliveryRequestDto;
import com.fiap.techchallenge4.ms_shipping.service.DeliveryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeliveryControllerTest {
    @Mock
    private DeliveryService deliveryService;

    @InjectMocks
    private DeliveryController deliveryController;

    @Test
    void testGetDeliveryByOrderId() {
        // Arrange
        Long orderId = 1L;
        DeliveryDto mockDeliveryDto = new DeliveryDto();
        mockDeliveryDto.setOrderId(orderId);

        when(deliveryService.getDeliveryByOrderId(orderId)).thenReturn(mockDeliveryDto);

        // Act
        ResponseEntity<DeliveryDto> response = deliveryController.getDeliveryByOrderId(orderId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(orderId, response.getBody().getOrderId());

        verify(deliveryService, times(1)).getDeliveryByOrderId(orderId);
    }

    @Test
    void testCreateDeliveryByOrderId() {
        // Arrange
        DeliveryRequestDto deliveryRequestDto = new DeliveryRequestDto();
        deliveryRequestDto.setOrderId(1L);
        DeliveryDto mockDeliveryDto = new DeliveryDto();
        mockDeliveryDto.setOrderId(1L);

        when(deliveryService.createDeliveryByOrderId(deliveryRequestDto)).thenReturn(mockDeliveryDto);

        // Act
        ResponseEntity<DeliveryDto> response = deliveryController.createDeliveryByOrderId(deliveryRequestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(deliveryRequestDto.getOrderId(), response.getBody().getOrderId());

        verify(deliveryService, times(1)).createDeliveryByOrderId(deliveryRequestDto);
    }

}
