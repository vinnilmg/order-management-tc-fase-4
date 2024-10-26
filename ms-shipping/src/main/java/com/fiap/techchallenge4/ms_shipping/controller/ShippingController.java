package com.fiap.techchallenge4.ms_shipping.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ShippingController {
    @GetMapping("/shippings")
    public String getShipping() {
        return "It works!";
    }
}
