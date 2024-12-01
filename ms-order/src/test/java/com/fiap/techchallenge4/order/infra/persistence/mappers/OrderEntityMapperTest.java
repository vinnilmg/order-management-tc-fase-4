package com.fiap.techchallenge4.order.infra.persistence.mappers;

import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.infra.persistence.entities.OrderEntity;
import com.fiap.techchallenge4.order.infra.persistence.entities.ProductEntity;
import com.fiap.techchallenge4.order.infra.persistence.entities.ShippingEntity;
import com.fiap.techchallenge4.order.helper.fixture.domain.OrderDomainFixture;
import com.fiap.techchallenge4.order.helper.fixture.domain.ProductDomainFixture;
import com.fiap.techchallenge4.order.helper.fixture.domain.ShippingDomainFixture;
import com.fiap.techchallenge4.order.helper.fixture.entity.OrderEntityFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.MockedStatic;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderEntityMapperTest {
    private OrderEntityMapper mapper;
    private ProductEntityMapper productEntityMapper;
    private ShippingEntityMapper shippingEntityMapper;

    @BeforeEach
    void init() {
        productEntityMapper = mock(ProductEntityMapper.class);
        shippingEntityMapper = mock(ShippingEntityMapper.class);
        mapper = new OrderEntityMapperImpl();
        ReflectionTestUtils.setField(mapper, "productEntityMapper", productEntityMapper);
        ReflectionTestUtils.setField(mapper, "shippingEntityMapper", shippingEntityMapper);
    }

    @Test
    void shouldReturnNullWhenOrderDomainIsNull() {
        final var result = mapper.toEntity(null);
        assertThat(result)
                .isNull();
    }

    @Test
    void shouldMapOrderEntity() {
        final var order = OrderDomainFixture.CRIADO();
        final var result = mapper.toEntity(order);

        when(productEntityMapper.toEntities(order.getProducts()))
                .thenReturn(List.of(mock(ProductEntity.class)));

        when(shippingEntityMapper.toEntity(order.getShipping()))
                .thenReturn(mock(ShippingEntity.class));

        assertThat(result)
                .isNotNull()
                .extracting(
                        OrderEntity::getCpf,
                        OrderEntity::getStatus,
                        OrderEntity::getTotal,
                        OrderEntity::getOrderCreationDate
                ).containsExactly(
                        order.getCpf(),
                        order.getStatus(),
                        order.getTotal(),
                        order.getCreationDate()
                );

        verify(productEntityMapper).toEntities(order.getProducts());
        verify(shippingEntityMapper).toEntity(order.getShipping());
    }

    @Test
    void shouldReturnNullWhenOrderEntityIsNull() {
        final var result = mapper.toDomains(null);
        assertThat(result)
                .isNull();
    }

    @Test
    void shouldMapOrderDomains() {
        final var order = OrderEntityFixture.CRIADO();

        when(productEntityMapper.toDomains(order.getProducts()))
                .thenReturn(List.of(ProductDomainFixture.FULL()));

        when(shippingEntityMapper.toDomain(order.getShipping()))
                .thenReturn(ShippingDomainFixture.FULL());

        try (MockedStatic<Mappers> mockMappers = mockStatic(Mappers.class)) {
            mockMappers.when(() -> Mappers.getMapper(ProductEntityMapper.class))
                    .thenReturn(productEntityMapper);

            mockMappers.when(() -> Mappers.getMapper(ShippingEntityMapper.class))
                    .thenReturn(shippingEntityMapper);

            final var result = mapper.toDomains(List.of(order));

            assertThat(result)
                    .isNotEmpty()
                    .hasSize(1)
                    .first()
                    .extracting(
                            Order::getId,
                            Order::getCpf,
                            Order::getStatus,
                            Order::getTotal,
                            Order::getCreationDate,
                            Order::getCompletionDate
                    ).containsExactly(
                            order.getId(),
                            order.getCpf(),
                            order.getStatus(),
                            order.getTotal(),
                            order.getOrderCreationDate(),
                            order.getOrderCompletionDate()
                    );
        }

        verify(productEntityMapper).toDomains(order.getProducts());
        verify(shippingEntityMapper).toDomain(order.getShipping());
    }
}
