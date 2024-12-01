package com.fiap.techchallenge4.order.infra.gateways.product;

import com.fiap.techchallenge4.order.helper.fixture.domain.ProductDomainFixture;
import com.fiap.techchallenge4.order.helper.fixture.exception.FeignExceptionFixture;
import com.fiap.techchallenge4.order.helper.fixture.response.ProviderProductResponseFixture;
import com.fiap.techchallenge4.order.infra.client.ProductClient;
import com.fiap.techchallenge4.order.infra.client.mappers.ProviderProductResponseMapper;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductClientGatewayTest {
    @InjectMocks
    private ProductClientGateway productClientGateway;
    @Mock
    private ProductClient productClient;
    @Mock
    private ProviderProductResponseMapper providerProductResponseMapper;

    @Test
    void shouldDecreaseStock() {
        final var sku = 3000L;
        final var quantity = 1;

        doNothing()
                .when(productClient)
                .decreaseStock(eq(sku), any());

        productClientGateway.decrease(sku, quantity);

        verify(productClient).decreaseStock(eq(sku), any());
    }

    @Test
    void shouldAddStock() {
        final var sku = 3000L;
        final var quantity = 1;

        doNothing()
                .when(productClient)
                .addStock(eq(sku), any());

        productClientGateway.add(sku, quantity);

        verify(productClient).addStock(eq(sku), any());
    }

    @Test
    void shouldReturnEmptyWhenResponseIsNull() {
        final var sku = 3000L;

        when(productClient.getProductBySku(sku))
                .thenReturn(null);

        final var result = productClientGateway.find(sku);
        assertThat(result)
                .isNotNull()
                .isEmpty();

        verify(productClient).getProductBySku(sku);
    }

    @Test
    void shouldFindProductBySku() {
        final var sku = 3000L;
        final var productResponse = ProviderProductResponseFixture.FULL();
        final var expectedProduct = ProductDomainFixture.FULL();

        when(productClient.getProductBySku(sku))
                .thenReturn(productResponse);

        when(providerProductResponseMapper.toProductDomain(productResponse))
                .thenReturn(expectedProduct);

        final var result = productClientGateway.find(sku);
        assertThat(result)
                .isNotNull()
                .isPresent()
                .hasValue(expectedProduct);

        verify(productClient).getProductBySku(sku);
        verify(providerProductResponseMapper).toProductDomain(productResponse);
    }

    @Test
    void shouldReturnEmptyWhenProductIsNotFound() {
        final var sku = 3000L;

        when(productClient.getProductBySku(sku))
                .thenThrow(FeignExceptionFixture.NOT_FOUND());

        final var result = productClientGateway.find(sku);
        assertThat(result)
                .isNotNull()
                .isEmpty();

        verify(productClient).getProductBySku(sku);
        verifyNoInteractions(providerProductResponseMapper);
    }

    @Test
    void shouldReturnFeignExceptionWhenAnyErrorOccurs() {
        final var sku = 3000L;

        when(productClient.getProductBySku(sku))
                .thenThrow(FeignExceptionFixture.INTERNAL_SERVER_ERROR());

        assertThatThrownBy(() -> productClientGateway.find(sku))
                .isInstanceOf(FeignException.class);

        verify(productClient).getProductBySku(sku);
        verifyNoInteractions(providerProductResponseMapper);
    }
}
