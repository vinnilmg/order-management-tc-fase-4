package com.fiap.techchallenge4.ms_customer.infra.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "payments")
public class PaymentEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String cardNumber;

    @NotNull
    private LocalDate expirationDate;

    @NotNull
    private String cvvCode;

    @OneToOne(mappedBy = "payment")
    private CustomerEntity customer;
}
