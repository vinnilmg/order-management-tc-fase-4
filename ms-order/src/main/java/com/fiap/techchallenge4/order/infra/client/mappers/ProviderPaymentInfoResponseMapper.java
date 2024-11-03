package com.fiap.techchallenge4.order.infra.client.mappers;

import com.fiap.techchallenge4.order.domain.entities.customer.payment.PaymentDomain;
import com.fiap.techchallenge4.order.infra.client.response.ProviderPaymentInfoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {ProviderAddressResponseMapper.class}
)
public interface ProviderPaymentInfoResponseMapper {

    @Mapping(target = "cardNumber", source = "cardNumber")
    @Mapping(target = "expirationDate", source = "expirationDate")
    @Mapping(target = "code", source = "code")
    PaymentDomain toPaymentDomain(ProviderPaymentInfoResponse customer);
}
