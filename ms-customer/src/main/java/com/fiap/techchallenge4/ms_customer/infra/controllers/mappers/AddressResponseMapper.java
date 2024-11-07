package com.fiap.techchallenge4.ms_customer.infra.controllers.mappers;

import com.fiap.techchallenge4.ms_customer.domain.entities.address.Address;
import com.fiap.techchallenge4.ms_customer.infra.controllers.response.AddressResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressResponseMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "street", source = "street")
    @Mapping(target = "number", source = "number")
    @Mapping(target = "complement", source = "complement")
    @Mapping(target = "district", source = "district")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "cep", source = "cep")
    AddressResponse toResponse(Address address);

    List<AddressResponse> toResponses(List<Address> addresses);

}
