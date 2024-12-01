package com.fiap.techchallenge4.ms_shipping.service;

import com.fiap.techchallenge4.ms_shipping.config.exception.NotFoundExpection;
import com.fiap.techchallenge4.ms_shipping.dto.ShippingDto;
import com.fiap.techchallenge4.ms_shipping.model.ShippingEntity;
import com.fiap.techchallenge4.ms_shipping.repository.ShippingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShippingServiceTest {

    @InjectMocks
    private ShippingService shippingService;

    @Mock
    private ShippingRepository shippingRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    void deveRetornarShippingDto_QuandoCepValido() {
        // Arrange
        String cep = "12345-678";
        ShippingEntity shippingEntity = new ShippingEntity();
        ShippingDto shippingDto = new ShippingDto();
        shippingDto.setPostalCode(cep);

        when(shippingRepository.findByCepWithinRange(cep)).thenReturn(Optional.of(shippingEntity));
        when(modelMapper.map(shippingEntity, ShippingDto.class)).thenReturn(shippingDto);

        // Act
        ShippingDto result = shippingService.getShippingByCep(cep);

        // Assert
        assertNotNull(result);
        assertEquals(cep, result.getPostalCode());
        verify(shippingRepository, times(1)).findByCepWithinRange(cep);
        verify(modelMapper, times(1)).map(shippingEntity, ShippingDto.class);
    }

    @Test
    void deveLancarNotFoundExpection_QuandoCepInvalido() {
        // Arrange
        String cep = "99999999";

        when(shippingRepository.findByCepWithinRange(cep)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundExpection exception = assertThrows(NotFoundExpection.class, () -> {
            shippingService.getShippingByCep(cep);
        });

        // Verifique a mensagem completa da exceção
        assertEquals("CEP 99999999 not found", exception.getMessage());  // Atualize aqui
        verify(shippingRepository, times(1)).findByCepWithinRange(cep);
        verifyNoInteractions(modelMapper);
    }

    @Test
    void deveRetornarListaDeShippingEntities_QuandoChamarGetAllRegion() {
        // Arrange
        List<ShippingEntity> shippingEntities = List.of(new ShippingEntity(), new ShippingEntity());

        when(shippingRepository.findAll()).thenReturn(shippingEntities);

        // Act
        List<ShippingEntity> result = shippingService.getAllRegion();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(shippingRepository, times(1)).findAll();
    }

    @Test
    void deveRetornarShippingDto_QuandoConverterParaDto() {
        // Arrange
        String cep = "12345-678";
        ShippingEntity shippingEntity = new ShippingEntity();
        ShippingDto shippingDto = new ShippingDto();

        when(modelMapper.map(shippingEntity, ShippingDto.class)).thenReturn(shippingDto);

        // Act
        ShippingDto result = shippingService.convertToDto(shippingEntity, cep);

        // Assert
        assertNotNull(result);
        assertEquals(cep, result.getPostalCode());
        verify(modelMapper, times(1)).map(shippingEntity, ShippingDto.class);
    }
}
