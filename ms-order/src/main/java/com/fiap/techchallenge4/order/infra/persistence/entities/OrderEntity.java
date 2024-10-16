package com.fiap.techchallenge4.order.infra.persistence.entities;

import com.fiap.techchallenge4.order.infra.persistence.entities.enums.OrderStatusEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class OrderEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String cpf;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    // TODO: Id entrega

    @NotNull
    private BigDecimal total;

    @NotNull
    private LocalDateTime orderCreationDate;

    private LocalDateTime orderCompletionDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.EAGER)
    private List<ProductEntity> products;
}