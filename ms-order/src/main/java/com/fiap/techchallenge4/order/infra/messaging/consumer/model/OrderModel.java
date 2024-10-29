package com.fiap.techchallenge4.order.infra.messaging.consumer.model;

import com.fiap.techchallenge4.order.domain.enums.OrderStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.isNull;

@Data
public class OrderModel {
    private Long id;
    private String cpf;
    private String status;
    private LocalDateTime creationDate;
    private List<ProductModel> products;
    private ShippingModel shipping;
    private BigDecimal total;
    private BigDecimal totalWithShipping;

    public boolean validate() {
        var isValid = true;

        if (isNull(id)) isValid = false;
        if (isNull(cpf)) isValid = false;
        if (isNull(status) || !status.equalsIgnoreCase(OrderStatusEnum.CRIADO.name())) isValid = false;
        if (isNull(creationDate)) isValid = false;
        if (isNull(total) || total.compareTo(BigDecimal.ZERO) < 1) isValid = false;
        if (isNull(totalWithShipping) || totalWithShipping.compareTo(BigDecimal.ZERO) < 1) isValid = false;
        if (isNull(status) || !shipping.validate()) isValid = false;
        if (isNull(products) || products.isEmpty() || products.stream().noneMatch(ProductModel::validate)) {
            isValid = false;
        }

        return isValid;
    }
}
