package com.fiap.techchallenge4.ms_customer.infra.controllers.mappers;

import com.fiap.techchallenge4.ms_customer.domain.entities.payment.Payment;
import com.fiap.techchallenge4.ms_customer.domain.entities.payment.PaymentDomain;
import com.fiap.techchallenge4.ms_customer.infra.controllers.request.PaymentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentRequestMapper {

    default Payment toPayment(final PaymentRequest request) {
        return new PaymentDomain(
                request.cardNumber(),
                request.expirationDate(),
                request.cvvCode()
        );
    }
}
