package com.fiap.techchallenge4.ms_customer.infra.controllers.mappers;

import com.fiap.techchallenge4.ms_customer.domain.entities.address.Address;
import com.fiap.techchallenge4.ms_customer.domain.entities.address.AddressDomain;
import com.fiap.techchallenge4.ms_customer.infra.controllers.request.AddressRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressRequestMapper {

    default Address toAddress(final AddressRequest request) {
        return new AddressDomain(
                request.street(),
                request.number(),
                request.complement(),
                request.district(),
                request.city(),
                request.state(),
                request.cep()
        );
    }
}
