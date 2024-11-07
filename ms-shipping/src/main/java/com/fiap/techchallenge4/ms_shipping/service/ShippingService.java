package com.fiap.techchallenge4.ms_shipping.service;

import com.fiap.techchallenge4.ms_shipping.config.exception.NotFoundExpection;
import com.fiap.techchallenge4.ms_shipping.dto.ShippingDto;
import com.fiap.techchallenge4.ms_shipping.repository.ShippingRepository;
import com.fiap.techchallenge4.ms_shipping.model.ShippingEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShippingService {

    @Autowired
    private ShippingRepository shippingRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ShippingDto getShippingByCep(String cep) {
        Optional<ShippingEntity> shippingEntity = shippingRepository.findByCepWithinRange(cep);
        if (shippingEntity.isEmpty()) {
            throw new NotFoundExpection(String.format("CEP %s", cep));
        }
        return convertToDto(shippingEntity.get(), cep);
    }

    public ShippingDto convertToDto(ShippingEntity shippingEntity, String cep){
        ShippingDto dto = modelMapper.map(shippingEntity, ShippingDto.class);
        dto.setPostalCode(cep);
        return dto;
    }
}
