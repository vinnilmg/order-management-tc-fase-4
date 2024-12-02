package com.fiap.techchallenge4.product.infrasctructure.config;

import com.fiap.techchallenge4.product.application.service.ProductService;
import com.fiap.techchallenge4.product.core.model.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataConfig {

    private final ProductService productService;

    public DataConfig(ProductService productService) {
        this.productService = productService;
    }
    @PostConstruct
    public void createData() {
        List<Product> productList = List.of(Product.builder()
                        .id(15000L)
                        .name("Teste 1")
                        .price(BigDecimal.valueOf(180))
                        .stock(100)
                        .description("Teste 1")
                        .skuId(15000L)
                .build(),
                Product.builder()
                        .id(15001L)
                        .name("Teste 2")
                        .price(BigDecimal.valueOf(180))
                        .stock(100)
                        .description("Teste 2")
                        .skuId(15000L)
                        .build()
                );
        productService.saveAll(productList);
    }


}
