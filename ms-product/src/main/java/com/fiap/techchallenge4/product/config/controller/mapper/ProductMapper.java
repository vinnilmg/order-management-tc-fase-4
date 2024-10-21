package com.fiap.techchallenge4.product.config.controller.mapper;

import com.fiap.techchallenge4.product.repository.entity.ProductData;
import com.fiap.techchallenge4.product.repository.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

   private final ModelMapper modelMapper = new ModelMapper();

    public ProductData toEntity(Product product) {
        return modelMapper.createTypeMap(Product.class, ProductData.class)
                .map(product);
    }

    public Product toModel(ProductData productData) {
        return modelMapper.createTypeMap(ProductData.class, Product.class)
                .map(productData);
    }
}
