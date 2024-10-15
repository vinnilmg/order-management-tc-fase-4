package com.fiap.techchallenge4.order.infra.controllers;

import com.fiap.techchallenge4.order.application.usecases.order.FindAllOrdersUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FindOrderByIdUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FindOrdersByCpfUseCase;
import com.fiap.techchallenge4.order.infra.controllers.mappers.OrderResponseMapper;
import com.fiap.techchallenge4.order.infra.controllers.response.OrderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final FindAllOrdersUseCase findAllOrdersUseCase;
    private final FindOrderByIdUseCase findOrderByIdUseCase;
    private final FindOrdersByCpfUseCase findOrdersByCpfUseCase;
    private final OrderResponseMapper orderResponseMapper;

    public OrderController(
            FindAllOrdersUseCase findAllOrdersUseCase,
            FindOrderByIdUseCase findOrderByIdUseCase,
            FindOrdersByCpfUseCase findOrdersByCpfUseCase,
            OrderResponseMapper orderResponseMapper
    ) {
        this.findAllOrdersUseCase = findAllOrdersUseCase;
        this.findOrderByIdUseCase = findOrderByIdUseCase;
        this.findOrdersByCpfUseCase = findOrdersByCpfUseCase;
        this.orderResponseMapper = orderResponseMapper;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAllOrders() {
        final var orders = findAllOrdersUseCase.find();
        return ResponseEntity.ok()
                .body(orderResponseMapper.toResponses(orders));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findOrderById(@PathVariable final Long orderId) {
        final var order = findOrderByIdUseCase.find(orderId);
        return ResponseEntity.ok()
                .body(orderResponseMapper.toResponse(order));
    }

    @GetMapping("/customers/{cpf}")
    public ResponseEntity<List<OrderResponse>> findCustomerOrdersByCpf(@PathVariable final String cpf) {
        final var orders = findOrdersByCpfUseCase.find(cpf);
        return ResponseEntity.ok()
                .body(orderResponseMapper.toResponses(orders));
    }
}
