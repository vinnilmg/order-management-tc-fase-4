package com.fiap.techchallenge4.ms_shipping.service;

import com.fiap.techchallenge4.ms_shipping.client.OrderServiceClient;
import com.fiap.techchallenge4.ms_shipping.config.exception.NotFoundExpection;
import com.fiap.techchallenge4.ms_shipping.controller.request.CourierUpdateRequest;
import com.fiap.techchallenge4.ms_shipping.model.CourierEntity;
import com.fiap.techchallenge4.ms_shipping.model.enums.AvaialableCourierEnum;
import com.fiap.techchallenge4.ms_shipping.repository.CourierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourierServiceTest {

    @Mock
    private CourierRepository repo;

    @Mock
    private OrderServiceClient orderServiceClient;

    @InjectMocks
    private CourierService courierService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        var courierList = List.of(new CourierEntity(), new CourierEntity());
        when(repo.findAll()).thenReturn(courierList);

        var result = courierService.findAll();

        assertEquals(2, result.size());
        verify(repo, times(1)).findAll();
    }

    @Test
    void testFindByIdSuccess() {
        var courier = new CourierEntity();
        when(repo.findById(1L)).thenReturn(Optional.of(courier));

        var result = courierService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(courier, result.get());
        verify(repo, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(repo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundExpection.class, () -> courierService.findById(1L));
        verify(repo, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        var courier = new CourierEntity();
        when(repo.save(courier)).thenReturn(courier);

        var result = courierService.save(courier);

        assertEquals(courier, result);
        verify(repo, times(1)).save(courier);
    }

    @Test
    void testFindByStatusAndRegionId() {
        var courierList = List.of(new CourierEntity());
        when(repo.findByStatusAndRegionId(AvaialableCourierEnum.AVAILABLE, 1L))
                .thenReturn(courierList);

        var result = courierService.findByStatusAndRegionId(AvaialableCourierEnum.AVAILABLE, 1L);

        assertEquals(1, result.size());
        verify(repo, times(1)).findByStatusAndRegionId(AvaialableCourierEnum.AVAILABLE, 1L);
    }

    @Test
    void testUpdateStatusSuccess() {
        var courier = new CourierEntity();
        courier.setStatus(AvaialableCourierEnum.AVAILABLE);

        when(repo.findById(1L)).thenReturn(Optional.of(courier));
        when(repo.save(courier)).thenReturn(courier);

        var request = new CourierUpdateRequest(AvaialableCourierEnum.ON_DELIVERY_ROUTE);
        request.setStatus(AvaialableCourierEnum.UNAVAILABLE);

        var result = courierService.updateStatus(1L, request);

        assertEquals(AvaialableCourierEnum.UNAVAILABLE, result.getStatus());
        verify(repo, times(1)).findById(1L);
        verify(repo, times(1)).save(courier);
    }

    @Test
    void testUpdateStatusNotFound() {
        when(repo.findById(1L)).thenReturn(Optional.empty());

        var request = new CourierUpdateRequest(AvaialableCourierEnum.ON_DELIVERY_ROUTE);
        request.setStatus(AvaialableCourierEnum.UNAVAILABLE);

        assertThrows(NotFoundExpection.class, () -> courierService.updateStatus(1L, request));
        verify(repo, times(1)).findById(1L);
    }

    @Test
    void testUpdateOrderToDeliveryRouteSuccess() {
        doNothing().when(orderServiceClient).updateOrderToDeliveryRoute(1L);

        assertDoesNotThrow(() -> courierService.updateOrderToDeliveryRoute(1L));
        verify(orderServiceClient, times(1)).updateOrderToDeliveryRoute(1L);
    }

    @Test
    void testUpdateOrderToDeliveryRouteFailure() {
        doThrow(new RuntimeException("Error")).when(orderServiceClient).updateOrderToDeliveryRoute(1L);

        assertThrows(NotFoundExpection.class, () -> courierService.updateOrderToDeliveryRoute(1L));
        verify(orderServiceClient, times(1)).updateOrderToDeliveryRoute(1L);
    }
}
