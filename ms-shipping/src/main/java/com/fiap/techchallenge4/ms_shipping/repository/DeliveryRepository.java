package com.fiap.techchallenge4.ms_shipping.repository;

import com.fiap.techchallenge4.ms_shipping.model.CourierEntity;
import com.fiap.techchallenge4.ms_shipping.model.DeliveryEntity;
import com.fiap.techchallenge4.ms_shipping.model.enums.RegionEnum;
import com.fiap.techchallenge4.ms_shipping.model.enums.ShippingStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {

    Optional<List<DeliveryEntity>> findByCourier(Optional<CourierEntity> courier);

    List<DeliveryEntity> findByStatusAndRegion(ShippingStatusEnum shippingStatusEnum, RegionEnum region);
}
