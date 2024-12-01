package com.fiap.techchallenge4.ms_customer.infra.persistence.mappers;

import com.fiap.techchallenge4.ms_customer.domain.entities.payment.Payment;
import com.fiap.techchallenge4.ms_customer.domain.entities.payment.PaymentDomain;
import com.fiap.techchallenge4.ms_customer.infra.persistence.entities.PaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentEntityMapper {

    @Mapping(target = "cardNumber", source = "cardNumber")
    @Mapping(target = "expirationDate", source = "expirationDate")
    @Mapping(target = "cvvCode", source = "cvvCode")
    PaymentEntity toEntity(Payment payment);

    default Payment toDomain(final PaymentEntity entity) {
        return PaymentDomain.of(
                entity.getId(),
                entity.getCardNumber(),
                entity.getExpirationDate(),
                entity.getCvvCode()
        );
    }

    default List<Payment> toDomains(final List<PaymentEntity> entities) {
        return entities.stream()
                .map(this::toDomain)
                .toList();
    }
}
