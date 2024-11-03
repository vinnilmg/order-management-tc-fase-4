package com.fiap.techchallenge4.order.infra.controllers;

import com.fiap.techchallenge4.order.application.usecases.order.CreateOrderUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FindAllOrdersUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FindOrderByIdUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FindOrdersByCpfUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.FinishOrderUseCase;
import com.fiap.techchallenge4.order.application.usecases.order.UpdateOrderShippingInfoUseCase;
import com.fiap.techchallenge4.order.infra.controllers.mappers.OrderRequestMapper;
import com.fiap.techchallenge4.order.infra.controllers.mappers.OrderResponseMapper;
import com.fiap.techchallenge4.order.infra.controllers.request.OrderRequest;
import com.fiap.techchallenge4.order.infra.controllers.response.OrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final FindAllOrdersUseCase findAllOrdersUseCase;
    private final FindOrderByIdUseCase findOrderByIdUseCase;
    private final FindOrdersByCpfUseCase findOrdersByCpfUseCase;
    private final CreateOrderUseCase createOrderUseCase;
    private final OrderResponseMapper orderResponseMapper;
    private final OrderRequestMapper orderRequestMapper;
    private final UpdateOrderShippingInfoUseCase updateOrderShippingInfoUseCase;
    private final FinishOrderUseCase finishOrderUseCase;

    public OrderController(
            FindAllOrdersUseCase findAllOrdersUseCase,
            FindOrderByIdUseCase findOrderByIdUseCase,
            FindOrdersByCpfUseCase findOrdersByCpfUseCase,
            CreateOrderUseCase createOrderUseCase,
            OrderResponseMapper orderResponseMapper,
            OrderRequestMapper orderRequestMapper,
            UpdateOrderShippingInfoUseCase updateOrderShippingInfoUseCase,
            FinishOrderUseCase finishOrderUseCase
    ) {
        this.findAllOrdersUseCase = findAllOrdersUseCase;
        this.findOrderByIdUseCase = findOrderByIdUseCase;
        this.findOrdersByCpfUseCase = findOrdersByCpfUseCase;
        this.createOrderUseCase = createOrderUseCase;
        this.orderResponseMapper = orderResponseMapper;
        this.orderRequestMapper = orderRequestMapper;
        this.updateOrderShippingInfoUseCase = updateOrderShippingInfoUseCase;
        this.finishOrderUseCase = finishOrderUseCase;
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

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody final OrderRequest request) {
        final var order = orderRequestMapper.toOrder(request);
        final var createdOrder = createOrderUseCase.execute(order);
        return ResponseEntity.ok()
                .body(orderResponseMapper.toResponse(createdOrder));
    }

    @PutMapping("/{orderId}/shipping")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrderToDeliveryRoute(@PathVariable final Long orderId) {
        updateOrderShippingInfoUseCase.update(orderId);
    }

    @PutMapping("/{orderId}/finish")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finishOrder(@PathVariable final Long orderId) {
        finishOrderUseCase.finish(orderId);
    }
}
