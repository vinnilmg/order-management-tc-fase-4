package com.fiap.techchallenge4.order.infra.client.mappers;

import com.fiap.techchallenge4.order.domain.entities.product.Product;
import com.fiap.techchallenge4.order.domain.entities.product.ProductDomain;
import com.fiap.techchallenge4.order.infra.client.response.ProviderProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProviderProductResponseMapper {

    default Product toProductDomain(final ProviderProductResponse response) {
        return ProductDomain.of(
                response.id(),
                response.sku(),
                response.stock(),
                response.price()
        );
    }
}
