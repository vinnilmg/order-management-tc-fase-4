package com.fiap.techchallenge4.ms_shipping.controller;

import com.fiap.techchallenge4.ms_shipping.dto.DeliveryDto;
import com.fiap.techchallenge4.ms_shipping.dto.request.DeliveryRequestDto;
import com.fiap.techchallenge4.ms_shipping.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/deliveries")
    public ResponseEntity<DeliveryDto> createDeliveryByOrderId(@RequestBody DeliveryRequestDto deliveryRequestDto) {
        final var result = deliveryService.createDeliveryByOrderId(deliveryRequestDto);
        return ResponseEntity.status(201).body(result);
    }
}
