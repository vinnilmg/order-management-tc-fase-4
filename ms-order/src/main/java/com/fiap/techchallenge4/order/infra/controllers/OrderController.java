package com.fiap.techchallenge4.order.infra.controllers;

import com.fiap.techchallenge4.order.application.usecases.order.FindOrderByIdUseCase;
import com.fiap.techchallenge4.order.infra.controllers.mappers.OrderResponseMapper;
import com.fiap.techchallenge4.order.infra.controllers.response.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final FindOrderByIdUseCase findOrderByIdUseCase;
    private final OrderResponseMapper orderResponseMapper;

    public OrderController(
            FindOrderByIdUseCase findOrderByIdUseCase,
            OrderResponseMapper orderResponseMapper
    ) {
        this.findOrderByIdUseCase = findOrderByIdUseCase;
        this.orderResponseMapper = orderResponseMapper;
    }

    @GetMapping
    public ResponseEntity<Void> findAllOrders() {
        return null;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findOrderById(@PathVariable final Long orderId) {
        final var order = findOrderByIdUseCase.find(orderId);
        return ResponseEntity.ok()
                .body(orderResponseMapper.toResponse(order));
    }

    @GetMapping("/customer/{cpf}")
    public ResponseEntity<Void> findCustomerOrdersByCpf(@PathVariable final String cpf) {
        return null;
    }
}
