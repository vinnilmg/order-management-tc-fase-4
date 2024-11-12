package com.fiap.techchallenge4.order.infra.gateways.product;

import com.fiap.techchallenge4.order.application.gateways.product.AddStockGateway;
import com.fiap.techchallenge4.order.application.gateways.product.DecreaseStockGateway;
import com.fiap.techchallenge4.order.application.gateways.product.FindProductGateway;
import com.fiap.techchallenge4.order.domain.entities.product.Product;
import com.fiap.techchallenge4.order.infra.client.ProductClient;
import com.fiap.techchallenge4.order.infra.client.mappers.ProviderProductResponseMapper;
import com.fiap.techchallenge4.order.infra.client.request.UpdateProductStockRequest;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class ProductClientGateway implements FindProductGateway, DecreaseStockGateway, AddStockGateway {
    private final ProductClient productClient;
    private final ProviderProductResponseMapper providerProductResponseMapper;

    public ProductClientGateway(
            ProductClient productClient,
            ProviderProductResponseMapper providerProductResponseMapper
    ) {
        this.productClient = productClient;
        this.providerProductResponseMapper = providerProductResponseMapper;
    }

    @Override
    public Optional<Product> find(final Long sku) {
        log.info("Buscando produto no microsserviço...");
        try {
            return Optional.ofNullable(productClient.getProductBySku(sku))
                    .map(providerProductResponseMapper::toProductDomain);
        } catch (FeignException e) {
            if (e.status() != HttpStatus.NOT_FOUND.value()) {
                throw e;
            }
        }
        return Optional.empty();
    }

    @Override
    public void decrease(final Long sku, final Integer quantity) {
        log.info("Diminuindo estoque do produto no microsserviço...");
        productClient.decreaseStock(sku, UpdateProductStockRequest.of(quantity));
    }

    @Override
    public void add(final Long sku, final Integer quantity) {
        log.info("Adicionando estoque do produto no microsserviço...");
        productClient.addStock(sku, UpdateProductStockRequest.of(quantity));
    }
}
