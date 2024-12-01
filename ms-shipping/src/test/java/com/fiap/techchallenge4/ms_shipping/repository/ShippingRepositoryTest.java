package com.fiap.techchallenge4.ms_shipping.repository;

import com.fiap.techchallenge4.ms_shipping.model.ShippingEntity;
import com.fiap.techchallenge4.ms_shipping.model.enums.RegionEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShippingRepositoryTest {

    @Mock
    private ShippingRepository shippingRepository;

    @Test
    void devePermitirBuscarShippingPorCep() {
        //arrange
        var cep = "12345678";
        var shipping = gerarShipping();
//        shipping.setCepStart("12345677");
//        shipping.setCepEnd("12345679");

        when(shippingRepository.findByCepWithinRange(cep)).thenReturn(Optional.of(shipping));

        //act
        var shippingEncontrado = shippingRepository.findByCepWithinRange(cep);
        //assert
        assertThat(shippingEncontrado).isPresent().containsSame(shipping);

        verify(shippingRepository, times(1)).findByCepWithinRange(cep);
    }

    @Test
    void deveRetornarVazioQuandoCepNaoEstiverNoIntervalo() {
        // Arrange
        String cep = "99999";
        when(shippingRepository.findByCepWithinRange(cep)).thenReturn(Optional.empty());

        // Act
        Optional<ShippingEntity> result = shippingRepository.findByCepWithinRange(cep);

        // Assert
        assertFalse(result.isPresent());
        verify(shippingRepository, times(1)).findByCepWithinRange(cep);
    }

    private ShippingEntity gerarShipping() {
        return ShippingEntity.builder().cepStart("01000000").cepEnd("39999998").daysToDelivery("3").region(RegionEnum.SUDESTE).deliveryPrice(BigDecimal.valueOf(10)).build();
    }

}
