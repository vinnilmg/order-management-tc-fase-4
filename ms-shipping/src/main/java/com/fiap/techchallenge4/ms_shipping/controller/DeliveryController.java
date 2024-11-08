package com.fiap.techchallenge4.ms_shipping.controller;

import com.fiap.techchallenge4.ms_shipping.dto.DeliveryDto;
import com.fiap.techchallenge4.ms_shipping.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping("/delivery/order/{orderId}")
    public ResponseEntity<DeliveryDto> getDeliveryByOrderId(@PathVariable Long orderId) {
        final var result = deliveryService.getDeliveryByOrderId(orderId);

        return ResponseEntity.status(200).body(result);
    }
}
