package com.fiap.techchallenge4.product.infrasctructure.batch.processor;

import com.fiap.techchallenge4.product.core.model.Product;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;

public class  ProductProcessor implements ItemProcessor<Product, Product> {
    @Override
    public Product process(Product item) throws Exception {
        item.setCreatedDateTime(LocalDateTime.now());
        return item;
    }
}
