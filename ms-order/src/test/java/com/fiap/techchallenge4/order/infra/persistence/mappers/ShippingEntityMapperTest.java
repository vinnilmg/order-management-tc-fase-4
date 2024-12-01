package com.fiap.techchallenge4.order.infra.persistence.mappers;

import com.fiap.techchallenge4.order.domain.entities.shipping.Shipping;
import com.fiap.techchallenge4.order.helper.fixture.domain.ShippingDomainFixture;
import com.fiap.techchallenge4.order.helper.fixture.entity.ShippingEntityFixture;
import com.fiap.techchallenge4.order.infra.persistence.entities.ShippingEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShippingEntityMapperTest {
    private ShippingEntityMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new ShippingEntityMapperImpl();
    }

    @Test
    void shouldMapShippingDomain() {
        final var shippingEntity = ShippingEntityFixture.FULL();
        final var result = mapper.toDomain(shippingEntity);
        assertThat(result)
                .isNotNull()
                .extracting(
                        Shipping::getPostalCode,
                        Shipping::getPrice,
                        Shipping::getDaysForDelivery
                ).containsExactly(
                        shippingEntity.getPostalCode(),
                        shippingEntity.getPrice(),
                        shippingEntity.getEstimate()
                );
    }

    @Test
    void shouldMapShippingEntity() {
        final var shipping = ShippingDomainFixture.FULL();
        final var result = mapper.toEntity(shipping);
        assertThat(result)
                .isNotNull()
                .extracting(
                        ShippingEntity::getPostalCode,
                        ShippingEntity::getPrice,
                        ShippingEntity::getEstimate,
                        ShippingEntity::getTimeUnit
                ).containsExactly(
                        shipping.getPostalCode(),
                        shipping.getPrice(),
                        shipping.getDaysForDelivery(),
                        shipping.getTimeUnit()
                );
    }
}
