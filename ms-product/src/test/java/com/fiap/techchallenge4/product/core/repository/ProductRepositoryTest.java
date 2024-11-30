package com.fiap.techchallenge4.product.core.repository;

import com.fiap.techchallenge4.product.core.entity.ProductData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("Deve encontrar um produto pelo SKU ID")
    void shouldFindProductBySkuId() {
        // Arrange
        ProductData product = new ProductData();
        product.setSkuId(123L);
        product.setName("Produto Teste");
        product.setPrice(new BigDecimal(100.0));

        when(productRepository.findBySkuId(123L)).thenReturn(Optional.of(product));
        // Act
        Optional<ProductData> result = productRepository.findBySkuId(123L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getSkuId()).isEqualTo(123L);
        assertThat(result.get().getName()).isEqualTo("Produto Teste");
        assertThat(result.get().getPrice()).isEqualTo(product.getPrice());
    }

    @Test
    @DisplayName("Não deve encontrar produto com SKU ID inexistente")
    void shouldNotFindProductByNonExistingSkuId() {
        // Act
        Optional<ProductData> result = productRepository.findBySkuId(999L);

        // Assert
        assertThat(result).isNotPresent();
    }
}
