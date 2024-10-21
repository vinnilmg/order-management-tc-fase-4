package com.fiap.techchallenge4.product.service;

import com.fiap.techchallenge4.product.repository.model.Product;

import java.util.List;

public interface ProductService {

     List<Product> findAll();

     Product save(Product productToSave);

     void saveAll(List<Product> productToSave);

}
