package com.fiap.techchallenge4.ms_shipping.repository;

import com.fiap.techchallenge4.ms_shipping.model.CourierEntity;
import com.fiap.techchallenge4.ms_shipping.model.enums.AvaialableCourierEnum;
import com.fiap.techchallenge4.ms_shipping.model.enums.RegionEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourierRepository extends JpaRepository<CourierEntity, Long> {

    List<CourierEntity> findByStatusAndRegion(AvaialableCourierEnum status, RegionEnum region);
}
