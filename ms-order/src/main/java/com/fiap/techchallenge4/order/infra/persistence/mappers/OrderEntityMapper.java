package com.fiap.techchallenge4.order.infra.persistence.mappers;

import com.fiap.techchallenge4.order.domain.entities.Order;
import com.fiap.techchallenge4.order.domain.entities.OrderDomain;
import com.fiap.techchallenge4.order.infra.persistence.entities.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderEntityMapper {

    default Order toDomain(final OrderEntity entity) {
        return OrderDomain.of(
                entity.getId(),
                entity.getCpf(),
                entity.getStatus().toString(), // TODO: Alterar
                entity.getTotal(),
                entity.getOrderCreationDate(),
                entity.getOrderCompletionDate()
        );
    }
}
