package com.fiap.techchallenge4.ms_shipping.service;

import com.fiap.techchallenge4.ms_shipping.client.OrderServiceClient;
import com.fiap.techchallenge4.ms_shipping.config.exception.NotFoundExpection;
import com.fiap.techchallenge4.ms_shipping.controller.request.CourierUpdateRequest;
import com.fiap.techchallenge4.ms_shipping.model.CourierEntity;
import com.fiap.techchallenge4.ms_shipping.model.enums.AvaialableCourierEnum;
import com.fiap.techchallenge4.ms_shipping.repository.CourierRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CourierService {

    @Autowired
    private CourierRepository repo;

    @Autowired
    private OrderServiceClient orderServiceClient;

    public Collection<CourierEntity> findAll() {
        return repo.findAll();
    }

    public Optional<CourierEntity> findById(Long id) {
        var courier = repo.findById(id);

        if (courier.isEmpty()) throw new NotFoundExpection(String.format("Courier %s", id));

        return courier;
    }

    public CourierEntity save(CourierEntity courier) {
        courier = repo.save(courier);

        return courier;
    }

    public List<CourierEntity> findByStatusAndRegionId(AvaialableCourierEnum status, Long region) {
        var courier = repo.findByStatusAndRegionId(status, region);

        return courier;
    }

    public CourierEntity updateStatus(Long id, CourierUpdateRequest request) {
        var courierOp = repo.findById(id);

        if (courierOp.isEmpty()) throw new NotFoundExpection("courier não encontrado");

        var courier = courierOp.get();
        courier.setStatus(request.getStatus());
        return repo.save(courier);
    }

    public void updateOrderToDeliveryRoute(Long orderId) {
        try {
            orderServiceClient.updateOrderToDeliveryRoute(orderId);
            log.info("Order finished whith id {}", orderId);
        }catch (Exception e) {
            log.error("Error finishing order with id {}", orderId);
            throw new NotFoundExpection(String.format("Error finishing order with id %s", orderId));
        }
    }
}
