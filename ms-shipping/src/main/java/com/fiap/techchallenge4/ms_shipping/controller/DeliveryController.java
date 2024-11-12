package com.fiap.techchallenge4.ms_shipping.controller;

import com.fiap.techchallenge4.ms_shipping.controller.request.DeliveryUpdateRequest;
import com.fiap.techchallenge4.ms_shipping.model.DeliveryEntity;
import com.fiap.techchallenge4.ms_shipping.model.enums.RegionEnum;
import com.fiap.techchallenge4.ms_shipping.model.enums.ShippingStatusEnum;
import com.fiap.techchallenge4.ms_shipping.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.lang.Long.parseLong;

@RestController
@RequestMapping("/api")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping("delivery-waiting/{region}")
    public ResponseEntity<List<DeliveryEntity>> findByStatusAndRegion(@PathVariable RegionEnum region) {
        var delivery = deliveryService.findByStatusAndRegion(ShippingStatusEnum.WAITING_FOR_DELIVERY, region);
        return ResponseEntity.ok(delivery);
    }

    @GetMapping("delivery-courier/{id}")
    public ResponseEntity<Optional<List<DeliveryEntity>>> findByCourier(@PathVariable String id) {
        var delivery = deliveryService.findByCourier(parseLong(id));
        return ResponseEntity.ok(delivery);
    }

    @PutMapping("delivery/updatestatus/{id}")
    public ResponseEntity<DeliveryEntity> updateStatus(@PathVariable Long id, @RequestBody DeliveryUpdateRequest delivery) {
        return ResponseEntity.ok(deliveryService.updateStatus(id, delivery));
    }
}
