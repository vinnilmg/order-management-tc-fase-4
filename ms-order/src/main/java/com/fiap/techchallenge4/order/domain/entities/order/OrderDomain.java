package com.fiap.techchallenge4.order.domain.entities.order;

import com.fiap.techchallenge4.order.domain.entities.product.Product;
import com.fiap.techchallenge4.order.domain.exceptions.CustomValidationException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import static com.fiap.techchallenge4.order.utils.FormatterUtils.formatCpf;
import static com.fiap.techchallenge4.order.utils.FormatterUtils.formatRealBrasileiro;
import static java.util.Objects.isNull;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

public class OrderDomain implements Order {
    private Long id;
    private String cpf;
    private String status;
    private BigDecimal total;
    private LocalDateTime creationDate;
    private LocalDateTime completionDate;
    private List<Product> products;

    public static Order of(
            final Long id,
            final String cpf,
            final String status,
            final BigDecimal total,
            final LocalDateTime creationDate,
            final LocalDateTime completionDate,
            final List<Product> products
    ) {
        return new OrderDomain(
                id,
                cpf,
                status,
                total,
                creationDate,
                completionDate,
                products
        );
    }

    public OrderDomain(
            final String cpf,
            final String status,
            final BigDecimal total,
            final LocalDateTime creationDate
    ) {
        this.cpf = cpfValidation(cpf);
        this.status = status;
        this.total = totalValidation(total);
        this.creationDate = creationDate;
    }

    private OrderDomain(
            final Long id,
            final String cpf,
            final String status,
            final BigDecimal total,
            final LocalDateTime creationDate,
            final LocalDateTime completionDate,
            final List<Product> products
    ) {
        this.id = id;
        this.cpf = cpfValidation(cpf);
        this.status = status;
        this.total = totalValidation(total);
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.products = productsValidation(products);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getCpf() {
        return cpf;
    }

    @Override
    public String getMaskedCpf() {
        return formatCpf(cpf);
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    @Override
    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public String getFormattedTotal() {
        return formatRealBrasileiro(total);
    }

    private static String cpfValidation(final String cpf) {
        if (isNull(cpf)) throw CustomValidationException.of("Customer CPF", "cannot be null");
        if (cpf.length() < 11) throw CustomValidationException.of("Customer CPF", "must be 11 positions");
        return cpf;
    }

    private static BigDecimal totalValidation(final BigDecimal total) {
        if (isNull(total)) throw CustomValidationException.of("Order Total", "cannot be null");
        if (total.signum() < 1) throw CustomValidationException.of("Order Total", "cannot be negative or zero");
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    private static List<Product> productsValidation(final List<Product> products) {
        if (isEmpty(products)) throw CustomValidationException.of("Order Products", "not exists");
        return products;
    }
}
