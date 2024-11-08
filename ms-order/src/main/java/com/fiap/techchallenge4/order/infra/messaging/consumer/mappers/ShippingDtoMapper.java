package com.fiap.techchallenge4.order.infra.messaging.consumer.mappers;

import com.fiap.techchallenge4.order.domain.entities.shipping.Shipping;
import com.fiap.techchallenge4.order.domain.entities.shipping.ShippingDomain;
import com.fiap.techchallenge4.order.infra.messaging.consumer.dto.ShippingDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ShippingDtoMapper {

    default Shipping toDomain(final ShippingDto shipping) {
        return ShippingDomain.of(
                shipping.getPostalCode(),
                shipping.getPrice(),
                shipping.getDaysForDelivery()
        );
    }
}
