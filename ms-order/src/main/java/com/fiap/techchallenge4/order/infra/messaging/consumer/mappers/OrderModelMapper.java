package com.fiap.techchallenge4.order.infra.messaging.consumer.mappers;

import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.domain.entities.order.OrderDomain;
import com.fiap.techchallenge4.order.infra.messaging.consumer.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderModelMapper {

    default Order toDomain(final OrderDto order) {
        final var products = order.getProducts().
                stream()
                .map(p -> Mappers.getMapper(ProductModelMapper.class).toDomain(p))
                .toList();

        final var shipping = Mappers.getMapper(ShippingModelMapper.class).toDomain(order.getShipping());

        return OrderDomain.of(
                order.getId(),
                order.getCpf(),
                order.getStatus(),
                order.getTotal(),
                order.getCreationDate(),
                null,
                products,
                shipping
        );
    }
}
