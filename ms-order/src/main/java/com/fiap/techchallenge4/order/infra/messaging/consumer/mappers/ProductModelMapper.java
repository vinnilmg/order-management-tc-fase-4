package com.fiap.techchallenge4.order.infra.messaging.consumer.mappers;

import com.fiap.techchallenge4.order.domain.entities.product.Product;
import com.fiap.techchallenge4.order.domain.entities.product.ProductDomain;
import com.fiap.techchallenge4.order.infra.messaging.consumer.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProductModelMapper {

    default Product toDomain(final ProductDto product) {
        return ProductDomain.of(
                product.getId(),
                product.getSku(),
                product.getQuantity(),
                product.getValue()
        );
    }
}
