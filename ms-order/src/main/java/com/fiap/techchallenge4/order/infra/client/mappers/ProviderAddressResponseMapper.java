package com.fiap.techchallenge4.order.infra.client.mappers;

import com.fiap.techchallenge4.order.domain.entities.customer.address.AddressDomain;
import com.fiap.techchallenge4.order.infra.client.response.ProviderAddressResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProviderAddressResponseMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "street", source = "street")
    @Mapping(target = "number", source = "number")
    @Mapping(target = "complement", source = "complement")
    @Mapping(target = "neighborhood", source = "neighborhood")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "state", source = "state")
    @Mapping(target = "postalCode", source = "postalCode")
    AddressDomain toAddressDomain(ProviderAddressResponse address);
}
