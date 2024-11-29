package com.fiap.techchallenge4.ms_shipping.repository;

import com.fiap.techchallenge4.ms_shipping.model.ShippingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ShippingRepository extends JpaRepository<ShippingEntity, Long> {

    @Query("SELECT s FROM ShippingEntity s WHERE :cep BETWEEN s.cepStart AND s.cepEnd")
    Optional<ShippingEntity> findByCepWithinRange(@Param("cep") String cep);

}
