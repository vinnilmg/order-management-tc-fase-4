package com.fiap.techchallenge4.order.infra.persistence.mappers;

import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.domain.entities.order.OrderDomain;
import com.fiap.techchallenge4.order.infra.persistence.entities.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {ProductEntityMapper.class, ShippingEntityMapper.class}
)
public interface OrderEntityMapper {

    default Order toDomain(final OrderEntity order) {
        return OrderDomain.of(
                order.getId(),
                order.getCpf(),
                order.getStatus(),
                order.getTotal(),
                order.getOrderCreationDate(),
                order.getOrderCompletionDate(),
                getMapper(ProductEntityMapper.class).toDomains(order.getProducts()),
                getMapper(ShippingEntityMapper.class).toDomain(order.getShipping())
        );
    }

    List<Order> toDomains(final List<OrderEntity> entities);

    @Mapping(target = "cpf", source = "cpf")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "total", source = "total")
    @Mapping(target = "orderCreationDate", source = "creationDate")
    @Mapping(target = "products", source = "products")
    @Mapping(target = "shipping", source = "shipping")
    OrderEntity toEntity(Order order);
}
