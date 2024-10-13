package com.fiap.techchallenge4.order.infra.persistence.entities;

import com.fiap.techchallenge4.order.infra.persistence.entities.enums.OrderStatusEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class OrderEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String cpf;

    // TODO: List<Produto>

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    // TODO: Id entrega

    @NotNull
    private BigDecimal total;

    @NotNull
    private LocalDateTime orderCreationDate;

    private LocalDateTime orderCompletionDate;
}
