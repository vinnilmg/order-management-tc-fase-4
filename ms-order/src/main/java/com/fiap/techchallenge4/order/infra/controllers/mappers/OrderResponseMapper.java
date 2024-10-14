package com.fiap.techchallenge4.order.infra.controllers.mappers;

import com.fiap.techchallenge4.order.domain.entities.Order;
import com.fiap.techchallenge4.order.infra.controllers.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderResponseMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "cpf", source = "maskedCpf")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "total", source = "total")
    @Mapping(target = "creationDate", source = "creationDate")
    @Mapping(target = "completionDate", source = "completionDate")
    OrderResponse toResponse(Order order);
}
