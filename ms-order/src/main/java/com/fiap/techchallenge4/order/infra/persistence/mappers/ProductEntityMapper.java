package com.fiap.techchallenge4.order.infra.persistence.mappers;

import com.fiap.techchallenge4.order.domain.entities.product.Product;
import com.fiap.techchallenge4.order.domain.entities.product.ProductDomain;
import com.fiap.techchallenge4.order.infra.persistence.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProductEntityMapper {

    default Product toDomain(final ProductEntity entity) {
        return ProductDomain.of(
                entity.getId(),
                entity.getSku(),
                entity.getPurchaseQuantity(),
                entity.getUnitaryValue()
        );
    }

    List<Product> toDomains(final List<ProductEntity> entities);
}
