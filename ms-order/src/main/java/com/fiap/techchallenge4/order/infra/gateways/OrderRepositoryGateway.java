package com.fiap.techchallenge4.order.infra.gateways;

import com.fiap.techchallenge4.order.application.gateways.OrderGateway;
import com.fiap.techchallenge4.order.domain.entities.Order;
import com.fiap.techchallenge4.order.infra.persistence.mappers.OrderEntityMapper;
import com.fiap.techchallenge4.order.infra.persistence.repositories.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OrderRepositoryGateway implements OrderGateway {
    private final OrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;

    public OrderRepositoryGateway(OrderRepository orderRepository, OrderEntityMapper orderEntityMapper) {
        this.orderRepository = orderRepository;
        this.orderEntityMapper = orderEntityMapper;
    }

    @Override
    public Optional<Order> findOrderById(final Long id) {
        return orderRepository.findById(id)
                .map(orderEntityMapper::toDomain);
    }
}
