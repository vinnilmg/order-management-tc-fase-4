package com.fiap.techchallenge4.ms_shipping.repository;

import com.fiap.techchallenge4.ms_shipping.model.DeliveryEntity;
import com.fiap.techchallenge4.ms_shipping.model.enums.DeliveryStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, Long> {
    Optional<DeliveryEntity> findByOrderId(Long orderId);

    List<DeliveryEntity> findByStatusAndRegionId(DeliveryStatusEnum shippingStatusEnum, Long region);

    Optional<List<DeliveryEntity>> findByCourierId(Long courierId);

    List<DeliveryEntity> findByStatusAndCourierIsNotNull(DeliveryStatusEnum status);

    int countByStatusAndCourierIsNotNull(DeliveryStatusEnum status);
}
