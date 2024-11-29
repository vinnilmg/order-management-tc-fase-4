package com.fiap.techchallenge4.product.application.controller.mapper;

import com.fiap.techchallenge4.product.core.entity.ProductData;
import com.fiap.techchallenge4.product.core.model.Product;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
public class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        productMapper = new ProductMapper(modelMapper);
    }

    @Test
    void testToEntity() {
        // Arrange
        Product product = Product.builder()
                .id(1L)
                .skuId(1001L)
                .name("Test Product")
                .description("Test Product Description")
                .price(new BigDecimal("99.99"))
                .stock(10)
                .createdDateTime(LocalDateTime.now())
                .lastDateTimeModified(LocalDateTime.now())
                .build();

        // Act
        ProductData productData = productMapper.toEntity(product);

        // Assert
        assertThat(productData)
                .isNotNull()
                .extracting(
                        ProductData::getId,
                        ProductData::getSkuId,
                        ProductData::getName,
                        ProductData::getDescription,
                        ProductData::getPrice,
                        ProductData::getStock,
                        ProductData::getCreatedDateTime,
                        ProductData::getLastDateTimeModified
                )
                .containsExactly(
                        product.getId(),
                        product.getSkuId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getStock(),
                        product.getCreatedDateTime(),
                        product.getLastDateTimeModified()
                );
    }

    @Test
    void testToModel() {
        // Arrange
        ProductData productData = ProductData.builder()
                .id(1L)
                .skuId(1001L)
                .name("Test Product")
                .description("Test Product Description")
                .price(new BigDecimal("99.99"))
                .stock(10)
                .createdDateTime(LocalDateTime.now())
                .lastDateTimeModified(LocalDateTime.now())
                .build();

        // Act
        Product product = productMapper.toModel(productData);

        // Assert
        assertThat(product)
                .isNotNull()
                .extracting(
                        Product::getId,
                        Product::getSkuId,
                        Product::getName,
                        Product::getDescription,
                        Product::getPrice,
                        Product::getStock,
                        Product::getCreatedDateTime,
                        Product::getLastDateTimeModified
                )
                .containsExactly(
                        productData.getId(),
                        productData.getSkuId(),
                        productData.getName(),
                        productData.getDescription(),
                        productData.getPrice(),
                        productData.getStock(),
                        productData.getCreatedDateTime(),
                        productData.getLastDateTimeModified()
                );
    }
}
