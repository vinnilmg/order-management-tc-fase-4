package com.fiap.techchallenge4.product.core.repository;

import com.fiap.techchallenge4.product.core.entity.ProductData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductData, Long> {

    Optional<ProductData> findBySkuId(Long skuId);
}
