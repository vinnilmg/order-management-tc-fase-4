package com.fiap.techchallenge4.order.infra.controllers.mappers;

import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.domain.entities.order.OrderDomain;
import com.fiap.techchallenge4.order.domain.entities.product.Product;
import com.fiap.techchallenge4.order.infra.controllers.request.OrderRequest;
import com.fiap.techchallenge4.order.infra.persistence.entities.enums.OrderStatusEnum;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static java.util.Objects.nonNull;
import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface OrderRequestMapper {

    default Order toOrder(final OrderRequest request) {
        final var products = new ArrayList<Product>();
        if (nonNull(request.products())) {
            request.products()
                    .forEach(product -> products.add(
                            getMapper(ProductRequestMapper.class).toProductDomain(product)
                    ));
        }

        return OrderDomain.of(
                request.cpf(),
                OrderStatusEnum.CRIADO.name(),
                LocalDateTime.now(),
                products
        );
    }
}
