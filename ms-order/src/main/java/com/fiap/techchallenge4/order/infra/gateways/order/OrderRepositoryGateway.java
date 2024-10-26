package com.fiap.techchallenge4.order.infra.gateways.order;

import com.fiap.techchallenge4.order.application.gateways.order.CreateOrderGateway;
import com.fiap.techchallenge4.order.application.gateways.order.FindAllOrdersGateway;
import com.fiap.techchallenge4.order.application.gateways.order.FindOrderByIdGateway;
import com.fiap.techchallenge4.order.application.gateways.order.FindOrdersByCpfGateway;
import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.infra.persistence.mappers.OrderEntityMapper;
import com.fiap.techchallenge4.order.infra.persistence.mappers.ProductEntityMapper;
import com.fiap.techchallenge4.order.infra.persistence.repositories.OrderRepository;
import com.fiap.techchallenge4.order.infra.persistence.repositories.ProductRepository;
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
        CreateOrderGateway {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderEntityMapper orderEntityMapper;
    private final ProductEntityMapper productEntityMapper;

    public OrderRepositoryGateway(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            OrderEntityMapper orderEntityMapper,
            ProductEntityMapper productEntityMapper
    ) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderEntityMapper = orderEntityMapper;
        this.productEntityMapper = productEntityMapper;
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

        entity.getProducts()
                .forEach(product -> product.setOrder(entity));

        final var createdOrder = orderRepository.save(entity);
        return orderEntityMapper.toDomain(createdOrder);
    }
}
