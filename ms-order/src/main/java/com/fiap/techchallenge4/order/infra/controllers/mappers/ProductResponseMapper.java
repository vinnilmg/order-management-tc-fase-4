package com.fiap.techchallenge4.order.infra.controllers.mappers;

import com.fiap.techchallenge4.order.domain.entities.product.Product;
import com.fiap.techchallenge4.order.infra.controllers.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProductResponseMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "sku", source = "sku")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "value", source = "formattedValue")
    ProductResponse toResponse(Product order);

    List<ProductResponse> toResponses(List<Product> orders);
}
