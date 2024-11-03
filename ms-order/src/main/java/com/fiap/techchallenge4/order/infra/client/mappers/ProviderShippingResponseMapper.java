package com.fiap.techchallenge4.order.infra.client.mappers;

import com.fiap.techchallenge4.order.domain.entities.shipping.ShippingDomain;
import com.fiap.techchallenge4.order.infra.client.response.ProviderShippingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProviderShippingResponseMapper {

    @Mapping(target = "postalCode", source = "postalCode")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "daysForDelivery", source = "daysForDelivery")
    ShippingDomain toShippingDomain(ProviderShippingResponse shipping);
}
