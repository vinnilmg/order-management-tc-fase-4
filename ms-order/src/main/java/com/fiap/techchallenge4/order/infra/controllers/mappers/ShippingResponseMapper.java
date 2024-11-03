package com.fiap.techchallenge4.order.infra.controllers.mappers;

import com.fiap.techchallenge4.order.domain.entities.shipping.Shipping;
import com.fiap.techchallenge4.order.infra.controllers.response.ShippingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ShippingResponseMapper {

    @Mapping(target = "postalCode", source = "postalCode")
    @Mapping(target = "price", source = "formattedPrice")
    @Mapping(target = "estimate", source = "estimate")
    ShippingResponse toResponse(Shipping shipping);

}
