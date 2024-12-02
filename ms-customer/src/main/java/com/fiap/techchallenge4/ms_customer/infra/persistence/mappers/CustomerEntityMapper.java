package com.fiap.techchallenge4.ms_customer.infra.persistence.mappers;

import com.fiap.techchallenge4.ms_customer.domain.entities.customer.Customer;
import com.fiap.techchallenge4.ms_customer.domain.entities.customer.CustomerDomain;
import com.fiap.techchallenge4.ms_customer.infra.persistence.entities.CustomerEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {AddressEntityMapper.class, PaymentEntityMapper.class})
public interface CustomerEntityMapper {

    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "birthDate", source = "birthDate")
    @Mapping(target = "payment", source = "payment")
    CustomerEntity toEntity(Customer customer);

    default Customer toDomain(final CustomerEntity entity) {
        final var addressDomain = Mappers.getMapper(AddressEntityMapper.class)
                .toDomain(entity.getAddress());
        final var paymentDomain = Mappers.getMapper(PaymentEntityMapper.class)
                .toDomain(entity.getPayment());

        return CustomerDomain.of(
                entity.getId(),
                entity.getCpf(),
                entity.getName(),
                addressDomain,
                entity.getBirthDate(),
                paymentDomain
        );
    }

    default List<Customer> toDomains(final List<CustomerEntity> entities) {
        return entities.stream()
                .map(this::toDomain)
                .toList();
    }

}
