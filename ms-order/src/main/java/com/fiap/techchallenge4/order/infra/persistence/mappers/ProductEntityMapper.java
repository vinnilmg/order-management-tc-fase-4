package com.fiap.techchallenge4.order.infra.persistence.mappers;

import com.fiap.techchallenge4.order.domain.entities.product.Product;
import com.fiap.techchallenge4.order.domain.entities.product.ProductDomain;
import com.fiap.techchallenge4.order.infra.persistence.entities.ProductEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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

    List<Product> toDomains(List<ProductEntity> entities);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "sku", source = "sku")
    @Mapping(target = "purchaseQuantity", source = "quantity")
    @Mapping(target = "unitaryValue", source = "value")
    ProductEntity toEntity(Product product);

    List<ProductEntity> toEntities(List<Product> domains);
}
