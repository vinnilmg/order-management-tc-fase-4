package com.fiap.techchallenge4.product.repository;

import com.fiap.techchallenge4.product.repository.entity.ProductData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductData,Long> {

}
