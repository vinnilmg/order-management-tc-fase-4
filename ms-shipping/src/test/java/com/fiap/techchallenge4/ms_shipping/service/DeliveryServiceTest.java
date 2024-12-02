package com.fiap.techchallenge4.ms_shipping.service;

import com.fiap.techchallenge4.ms_shipping.client.OrderServiceClient;
import com.fiap.techchallenge4.ms_shipping.config.exception.NotFoundExpection;
import com.fiap.techchallenge4.ms_shipping.dto.DeliveryDto;
import com.fiap.techchallenge4.ms_shipping.dto.request.DeliveryRequestDto;
import com.fiap.techchallenge4.ms_shipping.model.DeliveryEntity;
import com.fiap.techchallenge4.ms_shipping.model.ShippingEntity;
import com.fiap.techchallenge4.ms_shipping.model.enums.DeliveryStatusEnum;
import com.fiap.techchallenge4.ms_shipping.repository.DeliveryRepository;
import com.fiap.techchallenge4.ms_shipping.repository.ShippingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private ShippingRepository shippingRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private OrderServiceClient orderServiceClient;

    @InjectMocks
    private DeliveryService deliveryService;


    @Test
    void deveRetornarDeliveryPorOrderId_QuandoExiste() {
        // Arrange
        Long orderId = 1L;
        DeliveryEntity mockDelivery = new DeliveryEntity();
        mockDelivery.setOrderId(orderId);
        DeliveryDto mockDeliveryDto = new DeliveryDto();
        mockDeliveryDto.setOrderId(orderId);

        when(deliveryRepository.findByOrderId(orderId)).thenReturn(Optional.of(mockDelivery));
        when(modelMapper.map(mockDelivery, DeliveryDto.class)).thenReturn(mockDeliveryDto);

        // Act
        DeliveryDto result = deliveryService.getDeliveryByOrderId(orderId);

        // Assert
        assertNotNull(result);
        assertEquals(orderId, result.getOrderId());
        verify(deliveryRepository, times(1)).findByOrderId(orderId);
        verify(modelMapper, times(1)).map(mockDelivery, DeliveryDto.class);
    }

    @Test
    void deveLancarNotFoundExpection_QuandoOrderIdNaoExiste() {
        // Arrange
        Long orderId = 1L;

        when(deliveryRepository.findByOrderId(orderId)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundExpection exception = assertThrows(NotFoundExpection.class, () -> {
            deliveryService.getDeliveryByOrderId(orderId);
        });

        // Ajustando a mensagem esperada para incluir "not found"
        assertEquals("OrderId 1 not found", exception.getMessage());
        verify(deliveryRepository, times(1)).findByOrderId(orderId);
    }

    @Test
    void deveCriarNovaDelivery_QuandoDadosSaoValidos() {
        // Arrange
        String postalCode = "12345";
        Long orderId = 1L;

        DeliveryRequestDto deliveryRequestDto = new DeliveryRequestDto();
        deliveryRequestDto.setPostalCode(postalCode);
        deliveryRequestDto.setOrderId(orderId);

        ShippingEntity mockShipping = new ShippingEntity();
        mockShipping.setId(1L);

        DeliveryEntity mockDelivery = new DeliveryEntity();
        mockDelivery.setOrderId(orderId);
        mockDelivery.setStatus(DeliveryStatusEnum.WAITING_FOR_DELIVERY);

        DeliveryDto mockDeliveryDto = new DeliveryDto();
        mockDeliveryDto.setOrderId(orderId);

        when(shippingRepository.findByCepWithinRange(postalCode)).thenReturn(Optional.of(mockShipping));
        when(deliveryRepository.findByOrderId(orderId)).thenReturn(Optional.empty());
        when(deliveryRepository.save(any(DeliveryEntity.class))).thenReturn(mockDelivery);
        when(modelMapper.map(mockDelivery, DeliveryDto.class)).thenReturn(mockDeliveryDto);

        // Act
        DeliveryDto result = deliveryService.createDeliveryByOrderId(deliveryRequestDto);

        // Assert
        assertNotNull(result);
        assertEquals(orderId, result.getOrderId());
        verify(shippingRepository, times(1)).findByCepWithinRange(postalCode);
        verify(deliveryRepository, times(1)).findByOrderId(orderId);
        verify(deliveryRepository, times(1)).save(any(DeliveryEntity.class));
        verify(modelMapper, times(1)).map(mockDelivery, DeliveryDto.class);
    }

    @Test
    void deveLancarNotFoundExpection_QuandoPostalCodeNaoExiste() {
        // Arrange
        String postalCode = "99999";

        when(shippingRepository.findByCepWithinRange(postalCode)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundExpection exception = assertThrows(NotFoundExpection.class, () -> {
            deliveryService.createDeliveryByOrderId(new DeliveryRequestDto(1L, postalCode));
        });

        // Corrigir a mensagem esperada para incluir "not found"
        assertEquals("PostalCode 99999 not found", exception.getMessage());
        verify(shippingRepository, times(1)).findByCepWithinRange(postalCode);
    }

    @Test
    void deveFinalizarOrder_QuandoServicoNaoLancaErro() {
        // Arrange
        Long orderId = 1L;

        // Act
        deliveryService.finishOrder(orderId);

        // Assert
        verify(orderServiceClient, times(1)).finishOrder(orderId);
    }

    @Test
    void deveLancarNotFoundExpection_QuandoErroAoFinalizarOrder() {
        // Arrange
        Long orderId = 1L;

        doThrow(new RuntimeException("Erro interno")).when(orderServiceClient).finishOrder(orderId);

        // Act & Assert
        NotFoundExpection exception = assertThrows(NotFoundExpection.class, () -> {
            deliveryService.finishOrder(orderId);
        });

        // Verifique se a mensagem contém o início esperado
        assertTrue(exception.getMessage().startsWith("Error finishing order with id 1"));
        assertTrue(exception.getMessage().contains("Erro interno"));
        verify(orderServiceClient, times(1)).finishOrder(orderId);
    }
}

