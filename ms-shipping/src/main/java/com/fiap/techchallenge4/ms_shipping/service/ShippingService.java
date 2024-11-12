package com.fiap.techchallenge4.ms_shipping.service;

import com.fiap.techchallenge4.ms_shipping.config.exception.NotFoundExpection;
import com.fiap.techchallenge4.ms_shipping.dto.ShippingDto;
import com.fiap.techchallenge4.ms_shipping.repository.ShippingRepository;
import com.fiap.techchallenge4.ms_shipping.model.ShippingEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return modelMapper.map(shippingEntity.get(), ShippingDto.class);
    }

    public List<ShippingEntity> getAllRegion() {
        var shippingEntity = shippingRepository.findAll();

        return shippingEntity;
    }

}
