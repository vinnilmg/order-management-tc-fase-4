package com.fiap.techchallenge4.ms_shipping.service;

import com.fiap.techchallenge4.ms_shipping.config.exception.NotFoundExpection;
import com.fiap.techchallenge4.ms_shipping.controller.request.CourierUpdateRequest;
import com.fiap.techchallenge4.ms_shipping.model.CourierEntity;
import com.fiap.techchallenge4.ms_shipping.model.enums.AvaialableCourierEnum;
import com.fiap.techchallenge4.ms_shipping.model.enums.RegionEnum;
import com.fiap.techchallenge4.ms_shipping.repository.CourierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.synth.Region;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourierService {

    @Autowired
    private CourierRepository repo;

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
}
