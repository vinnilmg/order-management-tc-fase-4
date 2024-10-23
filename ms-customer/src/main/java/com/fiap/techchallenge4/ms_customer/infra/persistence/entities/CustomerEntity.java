package com.fiap.techchallenge4.ms_customer.infra.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "customers")
public class CustomerEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String cpf;

    @NotNull
    private String name;

    @NotNull
    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;

    @NotNull
    private LocalDate birthDate;

}
