package com.fiap.techchallenge4.order.infra.gateways.product;

import com.fiap.techchallenge4.order.application.gateways.product.FindProductGateway;
import com.fiap.techchallenge4.order.domain.entities.product.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class ProductClientGateway implements FindProductGateway {
    @Override
    public Optional<Product> find(final Long sku) {
        return Optional.empty();
    }
}
