package com.fiap.techchallenge4.ms_shipping.service;

import com.fiap.techchallenge4.ms_shipping.config.exception.NotFoundExpection;
import com.fiap.techchallenge4.ms_shipping.dto.DeliveryDto;
import com.fiap.techchallenge4.ms_shipping.model.DeliveryEntity;
import com.fiap.techchallenge4.ms_shipping.repository.DeliveryRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private ModelMapper modelMapper;

    public DeliveryDto getDeliveryByOrderId(Long orderId) {
        Optional<DeliveryEntity> deliveryEntity = deliveryRepository.findByOrderId(orderId);
        log.info("Searching for orderId: {}", orderId);
        if(deliveryEntity.isEmpty()) {
            log.error("OrderId not found: {}", orderId);
            throw new NotFoundExpection(String.format("OrderId %s", orderId));
        }
        return modelMapper.map(deliveryEntity.get(), DeliveryDto.class);
    }
}
