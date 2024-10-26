package com.fiap.techchallenge4.order.domain.entities.product;

import com.fiap.techchallenge4.order.domain.exceptions.CustomValidationException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.fiap.techchallenge4.order.utils.FormatterUtils.formatRealBrasileiro;
import static java.util.Objects.isNull;

public class ProductDomain implements Product {
    private Long id;
    private Long sku;
    private Integer quantity;
    private BigDecimal value;

    public static Product of(
            final Long id,
            final Long sku,
            final Integer quantity,
            final BigDecimal value
    ) {
        return new ProductDomain(id, sku, quantity, value);
    }

    public static Product of(
            final Long sku,
            final Integer quantity,
            final BigDecimal value
    ) {
        return new ProductDomain(sku, quantity, value);
    }

    private ProductDomain(
            final Long sku,
            final Integer quantity,
            final BigDecimal value
    ) {
        this.sku = skuValidation(sku);
        this.quantity = quantityValidation(quantity);
        this.value = valueValidation(value);
    }

    private ProductDomain(
            final Long id,
            final Long sku,
            final Integer quantity,
            final BigDecimal value
    ) {
        this.id = id;
        this.sku = skuValidation(sku);
        this.quantity = quantityValidation(quantity);
        this.value = valueValidation(value);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Long getSku() {
        return sku;
    }

    @Override
    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public String getFormattedValue() {
        return formatRealBrasileiro(value);
    }

    @Override
    public BigDecimal getTotal() {
        return value.multiply(BigDecimal.valueOf(quantity))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private static Long skuValidation(final Long sku) {
        if (isNull(sku) || sku < 1) throw new CustomValidationException("Product Sku", "cannot be null, zero or negative");
        return sku;
    }

    private static Integer quantityValidation(final Integer quantity) {
        if (isNull(quantity) || quantity < 1) throw new CustomValidationException("Product Quantity", "cannot be null, zero or negative");
        return quantity;
    }

    private static BigDecimal valueValidation(final BigDecimal value) {
        if (isNull(value) || value.signum() < 1) throw new CustomValidationException("Product Value", "cannot be null, zero or negative");
        return value;
    }
}
