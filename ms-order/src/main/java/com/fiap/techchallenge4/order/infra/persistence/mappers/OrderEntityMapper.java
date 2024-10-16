package com.fiap.techchallenge4.order.infra.persistence.mappers;

import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.domain.entities.order.OrderDomain;
import com.fiap.techchallenge4.order.infra.persistence.entities.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {ProductEntityMapper.class}
)
public interface OrderEntityMapper {

    default Order toDomain(final OrderEntity entity) {
        final var products = Mappers.getMapper(ProductEntityMapper.class)
                .toDomains(entity.getProducts());

        return OrderDomain.of(
                entity.getId(),
                entity.getCpf(),
                entity.getStatus().toString(), // TODO: Alterar
                entity.getTotal(),
                entity.getOrderCreationDate(),
                entity.getOrderCompletionDate(),
                products
        );
    }

    List<Order> toDomains(final List<OrderEntity> entities);
}
