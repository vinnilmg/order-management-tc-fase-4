package com.fiap.techchallenge4.order.infra.gateways.order;

import com.fiap.techchallenge4.order.application.gateways.order.CreateOrderGateway;
import com.fiap.techchallenge4.order.application.gateways.order.FindAllOrdersGateway;
import com.fiap.techchallenge4.order.application.gateways.order.FindOrderByIdGateway;
import com.fiap.techchallenge4.order.application.gateways.order.FindOrdersByCpfGateway;
import com.fiap.techchallenge4.order.application.gateways.order.FinishOrderGateway;
import com.fiap.techchallenge4.order.application.gateways.order.UpdateOrderStatusGateway;
import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.infra.persistence.mappers.OrderEntityMapper;
import com.fiap.techchallenge4.order.infra.persistence.repositories.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class OrderRepositoryGateway implements
        FindAllOrdersGateway,
        FindOrderByIdGateway,
        FindOrdersByCpfGateway,
        CreateOrderGateway,
        UpdateOrderStatusGateway,
        FinishOrderGateway {
    private final OrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;

    public OrderRepositoryGateway(OrderRepository orderRepository, OrderEntityMapper orderEntityMapper) {
        this.orderRepository = orderRepository;
        this.orderEntityMapper = orderEntityMapper;
    }

    @Override
    public Optional<Order> find(final Long id) {
        log.info("Buscando pedido por id na base...");
        return orderRepository.findById(id)
                .map(orderEntityMapper::toDomain);
    }

    @Override
    public List<Order> find() {
        log.info("Buscando todos os pedidos na base...");
        final var entities = orderRepository.findAll();
        return orderEntityMapper.toDomains(entities);
    }

    @Override
    public List<Order> find(final String cpf) {
        log.info("Buscando pedidos por cpf na base...");
        final var entities = orderRepository.findByCpf(cpf);
        return orderEntityMapper.toDomains(entities);
    }

    @Override
    public Order create(final Order order) {
        log.info("Criando pedido na base...");
        final var entity = orderEntityMapper.toEntity(order);

        entity.getProducts().forEach(product -> product.setOrder(entity));
        entity.getShipping().setOrder(entity);

        final var createdOrder = orderRepository.save(entity);
        return orderEntityMapper.toDomain(createdOrder);
    }

    @Override
    public Order update(final Order order) {
        log.info("Atualizando status do pedido na base...");
        orderRepository.updateStatus(order.getId(), order.getStatus());
        return order;
    }

    @Override
    public void finish(final Order order) {
        log.info("Finalizando pedido na base...");
        orderRepository.updateStatusAndCompletionDate(order.getId(), order.getStatus(), order.getCompletionDate());
    }
}
