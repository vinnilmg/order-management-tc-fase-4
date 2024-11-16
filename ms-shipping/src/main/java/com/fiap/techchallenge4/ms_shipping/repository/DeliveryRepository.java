package com.fiap.techchallenge4.ms_shipping.repository;

import com.fiap.techchallenge4.ms_shipping.model.CourierEntity;
import com.fiap.techchallenge4.ms_shipping.model.DeliveryEntity;
import com.fiap.techchallenge4.ms_shipping.model.enums.RegionEnum;
import com.fiap.techchallenge4.ms_shipping.model.enums.ShippingStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {
    Optional<DeliveryEntity> findByOrderId(Long orderId);

    //Optional<List<DeliveryEntity>> findByCourier(Optional<CourierEntity> courier);

    List<DeliveryEntity> findByStatusAndRegionId(ShippingStatusEnum shippingStatusEnum, Long region);

    Optional<List<DeliveryEntity>> findByCourierId(Long courierId);

}
