package com.fiap.techchallenge4.product.application.controller.mapper;

import com.fiap.techchallenge4.product.core.entity.ProductData;
import com.fiap.techchallenge4.product.core.model.Product;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProductMapper {

   private final ModelMapper modelMapper;

    public ProductData toEntity(Product product) {
        TypeMap<Product, ProductData> typeMap = modelMapper.getTypeMap(Product.class, ProductData.class);
        if (typeMap == null) {
            typeMap = modelMapper.createTypeMap(Product.class, ProductData.class);
        }
        return typeMap.map(product);
    }

    public Product toModel(ProductData productData) {
        TypeMap<ProductData, Product> typeMap = modelMapper.getTypeMap(ProductData.class, Product.class);
        if (typeMap == null) {
            typeMap = modelMapper.createTypeMap(ProductData.class, Product.class);
        }
        return typeMap.map(productData);
    }
}
