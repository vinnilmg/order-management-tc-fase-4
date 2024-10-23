package com.fiap.techchallenge4.order.infra.gateways.product;

import com.fiap.techchallenge4.order.application.gateways.product.FindProductGateway;
import com.fiap.techchallenge4.order.domain.entities.product.Product;
import com.fiap.techchallenge4.order.infra.client.ProductClient;
import com.fiap.techchallenge4.order.infra.client.mappers.ProviderProductResponseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class ProductClientGateway implements FindProductGateway {
    private final ProductClient productClient;
    private final ProviderProductResponseMapper providerProductResponseMapper;

    public ProductClientGateway(ProductClient productClient, ProviderProductResponseMapper providerProductResponseMapper) {
        this.productClient = productClient;
        this.providerProductResponseMapper = providerProductResponseMapper;
    }

    @Override
    public Optional<Product> find(final Long sku) {
        log.info("Buscando produto no microsserviço...");
        return Optional.ofNullable(productClient.getProductById(sku))
                .map(providerProductResponseMapper::toProductDomain);
    }
}
