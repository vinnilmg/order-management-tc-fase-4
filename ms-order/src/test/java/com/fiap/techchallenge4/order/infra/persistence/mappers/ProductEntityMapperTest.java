package com.fiap.techchallenge4.order.infra.persistence.mappers;

import com.fiap.techchallenge4.order.domain.entities.product.Product;
import com.fiap.techchallenge4.order.helper.fixture.domain.ProductDomainFixture;
import com.fiap.techchallenge4.order.helper.fixture.entity.ProductEntityFixture;
import com.fiap.techchallenge4.order.infra.persistence.entities.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductEntityMapperTest {
    private ProductEntityMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new ProductEntityMapperImpl();
    }

    @Test
    void shouldMapProductDomain() {
        final var productEntity = ProductEntityFixture.FULL();
        final var result = mapper.toDomain(productEntity);
        assertThat(result)
                .isNotNull()
                .extracting(
                        Product::getId,
                        Product::getSku,
                        Product::getQuantity,
                        Product::getValue
                ).containsExactly(
                        productEntity.getId(),
                        productEntity.getSku(),
                        productEntity.getPurchaseQuantity(),
                        productEntity.getUnitaryValue()
                );
    }

    @Test
    void shouldMapProductsDomains() {
        final var productsEntities = ProductEntityFixture.PRODUCTS();
        final var result = mapper.toDomains(productsEntities);
        assertThat(result)
                .isNotEmpty()
                .hasSize(productsEntities.size());
    }

    @Test
    void shouldMapProductEntity() {
        final var product = ProductDomainFixture.FULL();
        final var result = mapper.toEntity(product);
        assertThat(result)
                .isNotNull()
                .extracting(
                        ProductEntity::getSku,
                        ProductEntity::getPurchaseQuantity,
                        ProductEntity::getUnitaryValue
                ).containsExactly(
                        product.getSku(),
                        product.getQuantity(),
                        product.getValue()
                );
    }

    @Test
    void shouldMapProductsEntities() {
        final var products = ProductDomainFixture.PRODUCTS();
        final var result = mapper.toEntities(products);
        assertThat(result)
                .isNotEmpty()
                .hasSize(products.size());
    }
}
