package com.fiap.techchallenge4.order.infra.client.mappers;

import com.fiap.techchallenge4.order.domain.entities.customer.CustomerDomain;
import com.fiap.techchallenge4.order.infra.client.response.ProviderCustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {ProviderAddressResponseMapper.class}
)
public interface ProviderCustomerResponseMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "birthDate", source = "birthDate")
    @Mapping(target = "address", source = "address")
    CustomerDomain toCustomerDomain(ProviderCustomerResponse customer);
}
