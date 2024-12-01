package com.fiap.techchallenge4.ms_customer.infra.controllers.mappers;

import com.fiap.techchallenge4.ms_customer.domain.entities.customer.Customer;
import com.fiap.techchallenge4.ms_customer.infra.controllers.response.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {AddressResponseMapper.class, PaymentRequestMapper.class})
public interface CustomerResponseMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "birthDate", source = "birthDate")
    @Mapping(target = "payment", source = "payment")
    CustomerResponse toResponse(Customer customer);

    List<CustomerResponse> toResponses(List<Customer> customers);
}
