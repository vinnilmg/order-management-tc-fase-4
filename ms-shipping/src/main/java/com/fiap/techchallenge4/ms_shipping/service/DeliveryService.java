package com.fiap.techchallenge4.ms_shipping.service;

import com.fiap.techchallenge4.ms_shipping.config.exception.NotFoundExpection;
import com.fiap.techchallenge4.ms_shipping.dto.DeliveryDto;
import com.fiap.techchallenge4.ms_shipping.dto.request.DeliveryRequestDto;
import com.fiap.techchallenge4.ms_shipping.model.DeliveryEntity;
import com.fiap.techchallenge4.ms_shipping.model.ShippingEntity;
import com.fiap.techchallenge4.ms_shipping.model.enums.DeliveryStatusEnum;
import com.fiap.techchallenge4.ms_shipping.repository.DeliveryRepository;
import com.fiap.techchallenge4.ms_shipping.repository.ShippingRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    private final ShippingRepository shippingRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository, ShippingRepository shippingRepository, ModelMapper modelMapper) {
        this.deliveryRepository = deliveryRepository;
        this.shippingRepository = shippingRepository;
        this.modelMapper = modelMapper;
    }

    public DeliveryDto getDeliveryByOrderId(Long orderId) {
        Optional<DeliveryEntity> deliveryEntity = deliveryRepository.findByOrderId(orderId);
        log.info("Searching for orderId: {}", orderId);
        if(deliveryEntity.isEmpty()) {
            log.error("OrderId not found: {}", orderId);
            throw new NotFoundExpection(String.format("OrderId %s", orderId));
        }
        return modelMapper.map(deliveryEntity.get(), DeliveryDto.class);
    }

    public DeliveryDto createDeliveryByOrderId(DeliveryRequestDto deliveryRequestDto) {
        //pegando id do cep e verificando se ele está dentro do range
        log.info("searching for postalCode: {}", deliveryRequestDto.getPostalCode());
        final var postalCode = deliveryRequestDto.getPostalCode();
        ShippingEntity shipping = shippingRepository.findByCepWithinRange(postalCode)
                .orElseThrow(() -> new NotFoundExpection(String.format("PostalCode %s", postalCode)));

        //verificando se order id ja existe no banco
        final var orderIdRequest = deliveryRequestDto.getOrderId();
        if (deliveryRepository.findByOrderId(orderIdRequest).isPresent()) {
            log.error("OrderId already exists: {}", orderIdRequest);
            throw new NotFoundExpection(String.format("OrderId already exists:  %s", orderIdRequest));
            //TODO: Criar a classe de exceção correta
        }
        //criando delivery
        DeliveryEntity delivery = new DeliveryEntity();
        delivery.setOrderId(deliveryRequestDto.getOrderId());
        delivery.setStatus(DeliveryStatusEnum.WAITING_FOR_DELIVERY);
        delivery.setRegion(shipping);
        return modelMapper.map(deliveryRepository.save(delivery), DeliveryDto.class);
    }

    public Optional<List<DeliveryEntity>> findByCourier(Long id) {
        var courier = deliveryRepository.findByCourierId(id);
        //Optional<DeliveryEntity> delivery = Optional.empty();
        if (!courier.isEmpty()) {
            return courier;
        }
        if (courier.isEmpty()) throw new NotFoundExpection(String.format("Delivery for Courier %s", id));
        return Optional.empty();
    }

    public List<DeliveryEntity> findByStatusAndRegionId(DeliveryStatusEnum deliveryStatusEnum, Long region) {
        var delivery = deliveryRepository.findByStatusAndRegionId(deliveryStatusEnum, region);

        return delivery;
    }

    //verificar se posso usar no job direto o repository
//    public DeliveryEntity updateStatus(Long id, DeliveryUpdateRequest request) {
//        var deliveryOp = repo.findById(id);
//        var courier = courierRepository.findById(request.getId());
//
//        if (deliveryOp.isEmpty()) throw new NotFoundExpection("delivery não encontrado");
//        if (courier.isEmpty()) throw new NotFoundExpection("courier não encontrado");
//
//        var delivery = deliveryOp.get();
//        delivery.setStatus(request.getStatus());
//        delivery.setCourier(courier.get());
//        return repo.save(delivery);
//    }
}
