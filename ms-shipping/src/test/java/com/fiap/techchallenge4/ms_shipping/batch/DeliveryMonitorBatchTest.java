package com.fiap.techchallenge4.ms_shipping.batch;

import com.fiap.techchallenge4.ms_shipping.model.CourierEntity;
import com.fiap.techchallenge4.ms_shipping.model.DeliveryEntity;
import com.fiap.techchallenge4.ms_shipping.model.enums.AvaialableCourierEnum;
import com.fiap.techchallenge4.ms_shipping.model.enums.DeliveryStatusEnum;
import com.fiap.techchallenge4.ms_shipping.repository.CourierRepository;
import com.fiap.techchallenge4.ms_shipping.repository.DeliveryRepository;
import com.fiap.techchallenge4.ms_shipping.service.DeliveryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryMonitorBatchTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private CourierRepository courierRepository;

    @Mock
    private DeliveryService deliveryService;

    @InjectMocks
    private DeliveryMonitorBatch deliveryMonitorBatch;

    @Mock
    private ScheduledAnnotationBeanPostProcessor scheduledAnnotationBeanPostProcessor;

    @Test
    void testUpdateDeliveriesInTransit() {
        // Arrange
        DeliveryEntity deliveryInTransit = new DeliveryEntity();
        deliveryInTransit.setId(1L);
        deliveryInTransit.setLatitude(5);
        deliveryInTransit.setLongitude(5);
        deliveryInTransit.setStatus(DeliveryStatusEnum.ON_DELIVERY_ROUTE);

        CourierEntity courier = new CourierEntity();
        courier.setId(1L);
        courier.setStatus(AvaialableCourierEnum.UNAVAILABLE);  // Set a status for the courier

        deliveryInTransit.setCourier(courier);  // Set the courier to the delivery

        // Mock the repositories
        when(deliveryRepository.countByStatusAndCourierIsNotNull(DeliveryStatusEnum.ON_DELIVERY_ROUTE)).thenReturn(1);
        when(deliveryRepository.findByStatusAndCourierIsNotNull(DeliveryStatusEnum.ON_DELIVERY_ROUTE))
                .thenReturn(List.of(deliveryInTransit));

        when(courierRepository.getReferenceById(courier.getId())).thenReturn(courier);  // Mock courier repository

        // Act
        deliveryMonitorBatch.updateDeliveriesInTransit();

        // Assert
        assertEquals(6, deliveryInTransit.getLatitude());  // Latitude should have increased by 1
        assertEquals(6, deliveryInTransit.getLongitude()); // Longitude should have increased by 1
        verify(deliveryRepository, times(1)).save(deliveryInTransit);  // Ensure delivery is saved
        verify(deliveryService, times(0)).finishOrder(any());  // Since status hasn't reached "DELIVERED"

        // Simulate when latitude and longitude reach 10
        deliveryInTransit.setLatitude(9);
        deliveryInTransit.setLongitude(9);

        // Act again
        deliveryMonitorBatch.updateDeliveriesInTransit();

        // Assert delivery status update
        assertEquals(DeliveryStatusEnum.DELIVERED, deliveryInTransit.getStatus()); // Status should change to DELIVERED
        verify(courierRepository, times(1)).getReferenceById(deliveryInTransit.getCourier().getId());
        verify(deliveryService, times(1)).finishOrder(deliveryInTransit.getOrderId()); // Ensure finishOrder is called
    }
}
