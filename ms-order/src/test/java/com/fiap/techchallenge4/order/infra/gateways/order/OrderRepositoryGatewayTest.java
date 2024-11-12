package com.fiap.techchallenge4.order.infra.gateways.order;

import com.fiap.techchallenge4.order.helper.fixture.domain.OrderDomainFixture;
import com.fiap.techchallenge4.order.helper.fixture.entity.OrderEntityFixture;
import com.fiap.techchallenge4.order.infra.persistence.mappers.OrderEntityMapper;
import com.fiap.techchallenge4.order.infra.persistence.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.fiap.techchallenge4.order.helper.constants.OrderConstants.CPF;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderRepositoryGatewayTest {
    @InjectMocks
    private OrderRepositoryGateway orderRepositoryGateway;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderEntityMapper orderEntityMapper;

    @Test
    void shouldFindOrderById() {
        final var orderId = 1L;
        final var orderEntity = OrderEntityFixture.CRIADO();
        final var expectedOrder = OrderDomainFixture.CRIADO();

        when(orderRepository.findById(orderId))
                .thenReturn(Optional.of(orderEntity));

        when(orderEntityMapper.toDomain(orderEntity))
                .thenReturn(expectedOrder);

        final var result = orderRepositoryGateway.find(orderId);
        assertThat(result)
                .isNotNull()
                .isPresent()
                .hasValue(expectedOrder);

        verify(orderRepository).findById(orderId);
        verify(orderEntityMapper).toDomain(orderEntity);
    }

    @Test
    void shouldFindAllOrders() {
        final var orders = List.of(OrderEntityFixture.CRIADO());
        final var expectedOrders = OrderDomainFixture.ORDERS();

        when(orderRepository.findAll())
                .thenReturn(orders);

        when(orderEntityMapper.toDomains(orders))
                .thenReturn(expectedOrders);

        final var result = orderRepositoryGateway.find();
        assertThat(result)
                .isNotEmpty()
                .hasSize(orders.size())
                .isEqualTo(expectedOrders);

        verify(orderRepository).findAll();
        verify(orderEntityMapper).toDomains(orders);
    }

    @Test
    void shouldFindOrdersByCpf() {
        final var cpf = CPF;
        final var orders = List.of(OrderEntityFixture.CRIADO());
        final var expectedOrders = OrderDomainFixture.ORDERS();

        when(orderRepository.findByCpf(cpf))
                .thenReturn(orders);

        when(orderEntityMapper.toDomains(orders))
                .thenReturn(expectedOrders);

        final var result = orderRepositoryGateway.find(cpf);
        assertThat(result)
                .isNotEmpty()
                .hasSize(orders.size())
                .isEqualTo(expectedOrders);

        verify(orderRepository).findByCpf(cpf);
        verify(orderEntityMapper).toDomains(orders);
    }

    @Test
    void shouldCreateOrder() {
        final var order = OrderDomainFixture.CRIADO();
        final var orderEntity = OrderEntityFixture.CRIADO();

        when(orderEntityMapper.toEntity(order))
                .thenReturn(orderEntity);

        when(orderRepository.save(orderEntity))
                .thenReturn(orderEntity);

        when(orderEntityMapper.toDomain(orderEntity))
                .thenReturn(order);

        final var result = orderRepositoryGateway.create(order);
        assertThat(result)
                .isNotNull()
                .isEqualTo(order);

        verify(orderEntityMapper).toEntity(order);
        verify(orderRepository).save(orderEntity);
        verify(orderEntityMapper).toDomain(orderEntity);
    }

    @Test
    void shouldUpdateOrderStatus() {
        final var order = OrderDomainFixture.AGUARDANDO_ENVIO();

        doNothing()
                .when(orderRepository)
                .updateStatus(order.getId(), order.getStatus());

        final var result = orderRepositoryGateway.update(order);
        assertThat(result)
                .isNotNull()
                .isEqualTo(order);

        verify(orderRepository).updateStatus(order.getId(), order.getStatus());
    }

    @Test
    void shouldFinishOrder() {
        final var order = OrderDomainFixture.CRIADO();

        doNothing()
                .when(orderRepository)
                .updateStatusAndCompletionDate(
                        order.getId(),
                        order.getStatus(),
                        order.getCompletionDate()
                );

        orderRepositoryGateway.finish(order);

        verify(orderRepository)
                .updateStatusAndCompletionDate(order.getId(), order.getStatus(), order.getCompletionDate());
    }
}
