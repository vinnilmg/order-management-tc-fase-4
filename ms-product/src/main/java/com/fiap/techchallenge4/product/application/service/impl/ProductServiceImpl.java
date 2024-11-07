package com.fiap.techchallenge4.product.application.service.impl;

import com.fiap.techchallenge4.product.application.controller.mapper.ProductMapper;
import com.fiap.techchallenge4.product.application.exception.NotFoundException;
import com.fiap.techchallenge4.product.application.exception.ValidationException;
import com.fiap.techchallenge4.product.core.repository.ProductRepository;
import com.fiap.techchallenge4.product.core.model.Product;
import com.fiap.techchallenge4.product.application.service.ProductService;
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
    public Product findBySkuId(Long skuId) {
        return productRepository.findBySkuId(skuId)
                .map(productMapper::toModel)
                .orElseThrow(() ->  NotFoundException.of(
                        String.format("Product with skuId %s",skuId))
                );
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toModel)
                .orElseThrow(() ->  NotFoundException.of(
                        String.format("Product with id %s",id))
                );
    }

    @Override
    public void saveAll(List<Product> productToSave) {
        productRepository.saveAllAndFlush(productToSave.stream()
                .map(productMapper::toEntity)
                .toList());
    }

    @Override
    @Transactional
    public void decreaseStock(Long skuId, Integer quantity) {
        Product product = productRepository.findBySkuId(skuId)
                .map(productMapper::toModel)
                .orElseThrow(() -> NotFoundException.of(
                        String.format("Product with skuId %s",skuId))
                );

        if (product.getStock() < quantity) {
            throw new ValidationException(String.format("%s skuId product",skuId)
                    ,"has insufficient stock to decrease");
        }

        product.setStock(product.getStock() - quantity);

        productRepository.save(productMapper.toEntity(product));
    }

    @Override
    @Transactional
    public void addStock(Long skuId, Integer quantity) {

        Product product = productRepository.findBySkuId(skuId)
                .map(productMapper::toModel)
                .orElseThrow(() -> NotFoundException.of(
                        String.format("Product with skuId %s",skuId)));

        if (quantity <= 0) {
            throw new ValidationException(String.format("%s skuId product",skuId),
                    "Quantity cannot equals or less 0");
        }
        product.setStock(product.getStock() + quantity);
        productRepository.save(productMapper.toEntity(product));

    }
}
