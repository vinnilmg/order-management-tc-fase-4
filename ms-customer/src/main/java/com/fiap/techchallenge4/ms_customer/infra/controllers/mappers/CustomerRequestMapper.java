package com.fiap.techchallenge4.ms_customer.infra.controllers.mappers;

import com.fiap.techchallenge4.ms_customer.domain.entities.customer.Customer;
import com.fiap.techchallenge4.ms_customer.domain.entities.customer.CustomerDomain;
import com.fiap.techchallenge4.ms_customer.infra.controllers.request.CustomerRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {AddressRequestMapper.class, PaymentRequestMapper.class})
public interface CustomerRequestMapper {
    AddressRequestMapper addressMapper = Mappers.getMapper(AddressRequestMapper.class);
    PaymentRequestMapper paymentMapper = Mappers.getMapper(PaymentRequestMapper.class);

    default Customer toCustomer(final CustomerRequest request) {
        return new CustomerDomain(
                request.cpf(),
                request.name(),
                addressMapper.toAddress(request.address()),
                request.birthDate(),
                paymentMapper.toPayment(request.payment())
        );
    }
}
