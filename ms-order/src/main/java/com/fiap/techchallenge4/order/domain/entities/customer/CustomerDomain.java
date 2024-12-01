package com.fiap.techchallenge4.order.domain.entities.customer;

import com.fiap.techchallenge4.order.domain.entities.customer.address.Address;
import com.fiap.techchallenge4.order.domain.entities.customer.address.AddressDomain;

import static java.util.Objects.requireNonNull;

public class CustomerDomain implements Customer {
    private final Long id;
    private final String cpf;
    private final String name;
    private final String birthDate;
    private final Address address;

    public CustomerDomain(
            final Long id,
            final String cpf,
            final String name,
            final String birthDate,
            final AddressDomain address
    ) {
        this.id = requireNonNull(id, "Customer Id cannot be null");
        this.cpf = requireNonNull(cpf, "Customer CPF cannot be null");
        this.name = requireNonNull(name, "Customer Name cannot be null");
        this.birthDate = requireNonNull(birthDate, "Customer Birth Date cannot be null");
        this.address = requireNonNull(address, "Customer Address cannot be null");
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
    public String getName() {
        return name;
    }

    @Override
    public String getBirthDate() {
        return birthDate;
    }

    @Override
    public Address getAddress() {
        return address;
    }
}
