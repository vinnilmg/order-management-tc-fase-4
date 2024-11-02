package com.fiap.techchallenge4.product.service.impl;

import com.fiap.techchallenge4.product.controller.mapper.ProductMapper;
import com.fiap.techchallenge4.product.repository.ProductRepository;
import com.fiap.techchallenge4.product.model.Product;
import com.fiap.techchallenge4.product.service.ProductService;
import jakarta.transaction.Transactional;
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

    @Override
    @Transactional
    public void decreaseStock(Long id, Integer quantity) {
        Product product = productRepository.findById(id)
                .map(productMapper::toModel)
                .orElseThrow(() -> new RuntimeException("error"));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        product.setStock(product.getStock() - quantity);

        productRepository.save(productMapper.toEntity(product));
    }
}
