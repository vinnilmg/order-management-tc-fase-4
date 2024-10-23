package com.fiap.techchallenge4.ms_shipping.repository;

import com.fiap.techchallenge4.ms_shipping.repository.entities.ShippingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<ShippingEntity, Long> {
}
