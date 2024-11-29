package com.fiap.techchallenge4.ms_shipping.batch;

import com.fiap.techchallenge4.ms_shipping.controller.request.CourierUpdateRequest;
import com.fiap.techchallenge4.ms_shipping.model.CourierEntity;
import com.fiap.techchallenge4.ms_shipping.model.DeliveryEntity;
import com.fiap.techchallenge4.ms_shipping.model.ShippingEntity;
import com.fiap.techchallenge4.ms_shipping.model.enums.AvaialableCourierEnum;
import com.fiap.techchallenge4.ms_shipping.model.enums.DeliveryStatusEnum;
import com.fiap.techchallenge4.ms_shipping.repository.DeliveryRepository;
import com.fiap.techchallenge4.ms_shipping.service.CourierService;
import com.fiap.techchallenge4.ms_shipping.service.DeliveryService;
import com.fiap.techchallenge4.ms_shipping.service.ShippingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UpdateDeliveryBatchTest {

    @Mock
    private DeliveryService deliveryService;

    @Mock
    private CourierService courierService;

    @Mock
    private ShippingService shippingService;

    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private UpdateDeliveryBatch updateDeliveryBatch;

    private ShippingEntity shippingEntity;
    private CourierEntity courierEntity;
    private DeliveryEntity deliveryEntity;

    @BeforeEach
    public void setUp() {
        // Criação dos mocks de entidade
        shippingEntity = new ShippingEntity();
        shippingEntity.setId(1L);

        courierEntity = new CourierEntity();
        courierEntity.setId(1L);
        courierEntity.setStatus(AvaialableCourierEnum.AVAILABLE);

        deliveryEntity = new DeliveryEntity();
        deliveryEntity.setId(1L);
        deliveryEntity.setStatus(DeliveryStatusEnum.WAITING_FOR_DELIVERY);
        deliveryEntity.setCourier(null);
        deliveryEntity.setOrderId(123L);
    }

    @Test
    void testCheckDeliveryWhenNoCourierAvailable() {
        // Preparando o comportamento dos mocks
        when(shippingService.getAllRegion())
                .thenReturn(Collections.singletonList(shippingEntity));

        when(deliveryService.findByStatusAndRegionId(DeliveryStatusEnum.WAITING_FOR_DELIVERY, shippingEntity.getId()))
                .thenReturn(Collections.singletonList(deliveryEntity));

        when(courierService.findByStatusAndRegionId(AvaialableCourierEnum.AVAILABLE, shippingEntity.getId()))
                .thenReturn(Collections.emptyList());

        // Executando o batch
        updateDeliveryBatch.checkDelivery();

        // Verificando que o log de "entregador não disponível" foi chamado
        verify(courierService, Mockito.times(1)).findByStatusAndRegionId(AvaialableCourierEnum.AVAILABLE, shippingEntity.getId());
    }

    @Test
    void testCheckDeliveryWhenCourierIsAvailable() {
        // Preparando o comportamento dos mocks
        when(shippingService.getAllRegion())
                .thenReturn(Collections.singletonList(shippingEntity));

        when(deliveryService.findByStatusAndRegionId(DeliveryStatusEnum.WAITING_FOR_DELIVERY, shippingEntity.getId()))
                .thenReturn(Collections.singletonList(deliveryEntity));

        when(courierService.findByStatusAndRegionId(AvaialableCourierEnum.AVAILABLE, shippingEntity.getId()))
                .thenReturn(Arrays.asList(courierEntity));

        // Capturador do argumento para o updateStatus
        ArgumentCaptor<CourierUpdateRequest> captor = ArgumentCaptor.forClass(CourierUpdateRequest.class);

        // Executando o batch
        updateDeliveryBatch.checkDelivery();

        // Verificando que o método updateStatus foi chamado e capturando o argumento passado
        verify(courierService, times(1)).updateStatus(eq(1L), captor.capture());

        // Verificando o conteúdo do argumento capturado
        CourierUpdateRequest capturedRequest = captor.getValue();
        assert capturedRequest.getStatus() == AvaialableCourierEnum.ON_DELIVERY_ROUTE;

        // Verificando que a entrega foi salva com o courier atribuído
        verify(deliveryRepository, times(1)).save(deliveryEntity);

        // Verificando que a ordem foi atualizada para o status ON_DELIVERY_ROUTE
        verify(courierService, times(1)).updateOrderToDeliveryRoute(deliveryEntity.getOrderId());

    }

    @Test
    void testCheckDeliveryWhenNoDeliveriesFound() {
        // Preparando o comportamento dos mocks
        when(shippingService.getAllRegion())
                .thenReturn(Collections.singletonList(shippingEntity));

        when(deliveryService.findByStatusAndRegionId(DeliveryStatusEnum.WAITING_FOR_DELIVERY, shippingEntity.getId()))
                .thenReturn(Collections.emptyList());

        // Executando o batch
        updateDeliveryBatch.checkDelivery();

        // Verificando que o serviço de entrega foi chamado, mas não houve entregas
        verify(deliveryService, Mockito.times(1)).findByStatusAndRegionId(DeliveryStatusEnum.WAITING_FOR_DELIVERY, shippingEntity.getId());
    }
}
