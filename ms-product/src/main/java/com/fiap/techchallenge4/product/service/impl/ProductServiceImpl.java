package com.fiap.techchallenge4.product.service.impl;

import com.fiap.techchallenge4.product.config.controller.mapper.ProductMapper;
import com.fiap.techchallenge4.product.repository.ProductRepository;
import com.fiap.techchallenge4.product.repository.entity.ProductData;
import com.fiap.techchallenge4.product.repository.model.Product;
import com.fiap.techchallenge4.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toModel)
                .toList();
    }

    @Override
    public Product save(Product productToSave) {
        return productMapper.toModel(productRepository.save(productMapper.toEntity(productToSave)));
    }

    @Override
    public void saveAll(List<Product> productToSave) {
        productRepository.saveAllAndFlush(productToSave.stream()
                .map(productMapper::toEntity)
                .toList());
    }
}
