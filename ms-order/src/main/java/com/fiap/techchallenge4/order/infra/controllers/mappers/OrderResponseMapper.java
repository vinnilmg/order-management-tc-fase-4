package com.fiap.techchallenge4.order.infra.controllers.mappers;

import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.infra.controllers.response.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = ProductResponseMapper.class
)
public interface OrderResponseMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "cpf", source = "maskedCpf")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "total", source = "formattedTotal")
    @Mapping(target = "creationDate", source = "creationDate")
    @Mapping(target = "completionDate", source = "completionDate")
    @Mapping(target = "products", source = "products")
    OrderResponse toResponse(Order order);

    List<OrderResponse> toResponses(List<Order> orders);
}
