package com.fiap.techchallenge4.product.infra.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @GetMapping
    public ResponseEntity<Void> findAllOrders() {
        return null;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Void> findOrderById(@PathVariable final Long orderId) {
        return null;
    }

    @GetMapping("/customer/{cpf}")
    public ResponseEntity<Void> findCustomerOrdersByCpf(@PathVariable final String cpf) {
        return null;
    }
}
