package com.fiap.techchallenge4.order.infra.controllers.mappers;

import com.fiap.techchallenge4.order.domain.entities.product.Product;
import com.fiap.techchallenge4.order.domain.entities.product.ProductDomain;
import com.fiap.techchallenge4.order.infra.controllers.request.ProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProductRequestMapper {

    default Product toProductDomain(final ProductRequest request) {
        return ProductDomain.of(
                request.sku(),
                request.quantity(),
                request.unitaryValue()
        );
    }
}
