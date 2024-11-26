package com.fiap.techchallenge4.product.application.service;

import com.fiap.techchallenge4.product.core.model.Product;

import java.util.List;

public interface ProductService {

     List<Product> findAll();

     Product save(Product productToSave);

     Product findBySkuId(Long skuId);

     Product findById(Long id);

     void saveAll(List<Product> productToSave);

     void decreaseStock(Long id, Integer quantity);

     void addStock(Long id, Integer quantity);

}
