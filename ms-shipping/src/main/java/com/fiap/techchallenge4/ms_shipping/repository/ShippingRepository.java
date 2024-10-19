package com.fiap.techchallenge4.ms_shipping.repository;

import com.fiap.techchallenge4.ms_shipping.repository.entity.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<Shipping, Long> {
}
