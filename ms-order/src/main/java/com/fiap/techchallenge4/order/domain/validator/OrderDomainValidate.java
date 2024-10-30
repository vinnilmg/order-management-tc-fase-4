package com.fiap.techchallenge4.order.domain.validator;

import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.domain.entities.product.Product;
import com.fiap.techchallenge4.order.domain.entities.shipping.Shipping;
import com.fiap.techchallenge4.order.domain.enums.OrderStatusEnum;

import java.math.BigDecimal;

import static java.util.Objects.isNull;

public class OrderDomainValidate {

    public static boolean validate(final Order domain) {
        var isValid = true;

        if (isNull(domain.getId())) isValid = false;
        if (isNull(domain.getCpf())) isValid = false;
        if (isNull(domain.getStatus()) || !domain.getStatus().equalsIgnoreCase(OrderStatusEnum.CRIADO.name()))
            isValid = false;
        if (isNull(domain.getCreationDate())) isValid = false;
        if (isNull(domain.getTotal()) || domain.getTotal().compareTo(BigDecimal.ZERO) < 1) isValid = false;
        if (isNull(domain.getTotalWithShipping()) || domain.getTotalWithShipping().compareTo(BigDecimal.ZERO) < 1)
            isValid = false;
        if (isNull(domain.getShipping()) || !validate(domain.getShipping())) isValid = false;
        if (isNull(domain.getProducts()) || domain.getProducts().isEmpty() || domain.getProducts().stream().noneMatch(OrderDomainValidate::validate)) {
            isValid = false;
        }

        return isValid;
    }

    private static boolean validate(final Shipping domain) {
        var isValid = true;

        if (isNull(domain.getPostalCode())) isValid = false;
        if (isNull(domain.getPrice())) isValid = false;
        if (isNull(domain.getDaysForDelivery())) isValid = false;

        return isValid;
    }

    private static boolean validate(final Product domain) {
        var isValid = true;

        if (isNull(domain.getId())) isValid = false;
        if (isNull(domain.getSku())) isValid = false;
        if (isNull(domain.getQuantity()) || domain.getQuantity() < 1) isValid = false;
        if (isNull(domain.getValue()) || domain.getValue().compareTo(BigDecimal.ZERO) < 1) isValid = false;
        if (isNull(domain.getTotal()) || domain.getTotal().compareTo(BigDecimal.ZERO) < 1) isValid = false;

        return isValid;
    }
}
