package com.fiap.techchallenge4.ms_shipping.controller;

import com.fiap.techchallenge4.ms_shipping.dto.ShippingDto;
import com.fiap.techchallenge4.ms_shipping.service.ShippingService;
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
class ShippingControllerTest {

    @Mock
    private ShippingService shippingService;

    @InjectMocks
    private ShippingController shippingController;

    @Test
    void testGetShippingByCep() {
        // Arrange
        String cep = "99999999";
        ShippingDto mockShippingDto = new ShippingDto();
        mockShippingDto.setPostalCode(cep);

        when(shippingService.getShippingByCep(cep)).thenReturn(mockShippingDto);

        // Act
        ResponseEntity<ShippingDto> response = shippingController.getShipping(cep);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(cep, response.getBody().getPostalCode());

        verify(shippingService, times(1)).getShippingByCep(cep);
    }
}
