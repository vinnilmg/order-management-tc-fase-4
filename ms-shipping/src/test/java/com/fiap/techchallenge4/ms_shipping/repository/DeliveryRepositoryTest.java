package com.fiap.techchallenge4.ms_shipping.repository;

import com.fiap.techchallenge4.ms_shipping.model.CourierEntity;
import com.fiap.techchallenge4.ms_shipping.model.DeliveryEntity;
import com.fiap.techchallenge4.ms_shipping.model.ShippingEntity;
import com.fiap.techchallenge4.ms_shipping.model.enums.DeliveryStatusEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryRepositoryTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @Test
    void devePermitirBuscasMensagemPorOrderId() {
        //arrange
        var id = 1L;
        var orderId = 1L;
        var delivery = gerarDelivery();
        delivery.setId(id);
        delivery.setOrderId(orderId);

        when(deliveryRepository.findByOrderId(orderId)).thenReturn(Optional.of(delivery));

        //act
        var deliveryEncontrado = deliveryRepository.findByOrderId(orderId);
        //assert
        assertThat(deliveryEncontrado).isPresent().containsSame(delivery);

        deliveryEncontrado.ifPresent(deliveryEntity -> {
            assertThat(deliveryEntity.getId()).isEqualTo(id);
            assertThat(deliveryEntity.getOrderId()).isEqualTo(orderId);
        });
        verify(deliveryRepository, times(1)).findByOrderId(orderId);

    }

    @Test
    void devePermitirBuscarDeliveryPorId(){
        //arrange
        var id = 1L;
        var orderId = 1L;
        var delivery = gerarDelivery();
        delivery.setId(id);
        delivery.setOrderId(orderId);

        when(deliveryRepository.findById(id)).thenReturn(Optional.of(delivery));

        //act
        var deliveryEncontrado = deliveryRepository.findById(id);
        //assert
        assertThat(deliveryEncontrado).isPresent().containsSame(delivery);

        deliveryEncontrado.ifPresent(deliveryEntity -> {
            assertThat(deliveryEntity.getId()).isEqualTo(id);
            assertThat(deliveryEntity.getOrderId()).isEqualTo(orderId);
        });
        verify(deliveryRepository, times(1)).findById(id);
    }

    @Test
    void devePermitirBuscarDeliveryPorStatusERegiao() {
        //arrange
        var id = 1L;
        var orderId = 1L;
        var region = 1L;
        gerarDelivery().setId(id);
        gerarDelivery().setOrderId(orderId);
        gerarDelivery().setRegion(ShippingEntity.builder().id(region).build());

        when(deliveryRepository.findByStatusAndRegionId(DeliveryStatusEnum.ON_DELIVERY_ROUTE, region)).thenReturn(List.of(gerarDelivery()));

        //act
        var deliveryEncontrado = deliveryRepository.findByStatusAndRegionId(DeliveryStatusEnum.ON_DELIVERY_ROUTE, region);

        //assert
        assertThat(deliveryEncontrado).isNotEmpty().contains(gerarDelivery());
        verify(deliveryRepository, times(1)).findByStatusAndRegionId(DeliveryStatusEnum.ON_DELIVERY_ROUTE, region);

    }

    @Test
    void devePermitirBuscarDeliveryPorCourierId() {
        //arrange
        var id = 1L;
        var courierId = 1L;
        var orderId = 1L;
        gerarDelivery().setId(id);
        gerarDelivery().setOrderId(orderId);
        gerarDelivery().setCourier(CourierEntity.builder().id(courierId).build());

        when(deliveryRepository.findByCourierId(courierId)).thenReturn(Optional.of(List.of(gerarDelivery())));

        //act
        var deliveryEncontrado = deliveryRepository.findByCourierId(courierId);

        //assert
        assertThat(deliveryEncontrado).isPresent().contains(List.of(gerarDelivery()));
        verify(deliveryRepository, times(1)).findByCourierId(id);

    }


    private DeliveryEntity gerarDelivery(){
        return DeliveryEntity.builder().latitude(1).longitude(1).orderId(null).courier(null).status(DeliveryStatusEnum.WAITING_FOR_DELIVERY).region(null).build();
    }
}
