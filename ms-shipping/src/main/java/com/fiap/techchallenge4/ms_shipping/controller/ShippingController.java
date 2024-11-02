package com.fiap.techchallenge4.ms_shipping.controller;

import com.fiap.techchallenge4.ms_shipping.dto.ShippingDto;
import com.fiap.techchallenge4.ms_shipping.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;


    @GetMapping("/shippings/{cep}")
    public ResponseEntity<ShippingDto> getShipping(@PathVariable String cep) {
        return ResponseEntity.ok(shippingService.getShippingByCep(cep));
    }
}
