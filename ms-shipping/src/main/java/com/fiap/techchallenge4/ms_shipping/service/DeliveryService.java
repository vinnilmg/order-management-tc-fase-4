package com.fiap.techchallenge4.ms_shipping.service;

import com.fiap.techchallenge4.ms_shipping.config.exception.NotFoundExpection;
import com.fiap.techchallenge4.ms_shipping.controller.request.DeliveryUpdateRequest;
import com.fiap.techchallenge4.ms_shipping.model.DeliveryEntity;
import com.fiap.techchallenge4.ms_shipping.model.enums.RegionEnum;
import com.fiap.techchallenge4.ms_shipping.model.enums.ShippingStatusEnum;
import com.fiap.techchallenge4.ms_shipping.repository.CourierRepository;
import com.fiap.techchallenge4.ms_shipping.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository repo;

    @Autowired
    private CourierRepository courierRepository;

    public Optional<List<DeliveryEntity>> findByCourier(Long id) {
        var courier = courierRepository.findById(id);
        //Optional<DeliveryEntity> delivery = Optional.empty();
        if (!courier.isEmpty()) {
            var delivery = repo.findByCourier(courier);
            if (delivery.isEmpty()) throw new NotFoundExpection(String.format("Delivery for Courier %s", id));
            return delivery;
        }
        return Optional.empty();
    }

    public List<DeliveryEntity> findByStatusAndRegion(ShippingStatusEnum shippingStatusEnum, RegionEnum region) {
        var delivery = repo.findByStatusAndRegion(shippingStatusEnum, region);

        return delivery;
    }

    public DeliveryEntity updateStatus(Long id, DeliveryUpdateRequest request) {
        var deliveryOp = repo.findById(id);
        var courier = courierRepository.findById(request.getId());

        if (deliveryOp.isEmpty()) throw new NotFoundExpection("delivery não encontrado");
        if (courier.isEmpty()) throw new NotFoundExpection("courier não encontrado");

        var delivery = deliveryOp.get();
        delivery.setStatus(request.getStatus());
        delivery.setCourier(courier.get());
        return repo.save(delivery);
    }
}
