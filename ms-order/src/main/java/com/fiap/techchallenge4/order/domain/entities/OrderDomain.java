package com.fiap.techchallenge4.order.domain.entities;

import com.fiap.techchallenge4.order.domain.exceptions.CustomValidationException;

import javax.swing.text.MaskFormatter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;

public class OrderDomain implements Order {
    private Long id;
    private String cpf;
    private String status;
    private BigDecimal total;
    private LocalDateTime creationDate;
    private LocalDateTime completionDate;

    public static Order of(
            final Long id,
            final String cpf,
            final String status,
            final BigDecimal total,
            final LocalDateTime creationDate,
            final LocalDateTime completionDate
    ) {
        return new OrderDomain(
                id,
                cpf,
                status,
                total,
                creationDate,
                completionDate
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
            final LocalDateTime completionDate
    ) {
        this.id = id;
        this.cpf = cpfValidation(cpf);
        this.status = status;
        this.total = totalValidation(total);
        this.creationDate = creationDate;
        this.completionDate = completionDate;
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
        MaskFormatter mf;
        try {
            mf = new MaskFormatter("###.###.###-##");
            mf.setValueContainsLiteralCharacters(false);
            return mf.valueToString(cpf);
        } catch (ParseException ex) {
            return cpf;
        }
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
}
