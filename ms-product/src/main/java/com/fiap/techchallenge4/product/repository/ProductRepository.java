package com.fiap.techchallenge4.product.repository;

import com.fiap.techchallenge4.product.entity.ProductData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductData,Long> {

}
