package com.fiap.techchallenge4.order.infra.persistence.mappers;

import com.fiap.techchallenge4.order.domain.entities.shipping.Shipping;
import com.fiap.techchallenge4.order.domain.entities.shipping.ShippingDomain;
import com.fiap.techchallenge4.order.infra.persistence.entities.ShippingEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ShippingEntityMapper {

    default Shipping toDomain(final ShippingEntity entity) {
        return ShippingDomain.of(
                entity.getPostalCode(),
                entity.getPrice(),
                entity.getEstimate()
        );
    }

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "postalCode", source = "postalCode")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "estimate", source = "daysForDelivery")
    @Mapping(target = "timeUnit", source = "timeUnit")
    ShippingEntity toEntity(Shipping shipping);
}
