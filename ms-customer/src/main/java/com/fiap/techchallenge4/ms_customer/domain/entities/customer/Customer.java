package com.fiap.techchallenge4.ms_customer.domain.entities.customer;

import com.fiap.techchallenge4.ms_customer.domain.entities.address.Address;

import java.time.LocalDate;

public interface Customer {
    Long getId();
    String getCpf();
    String getName();
    Address getAddress();
    LocalDate getBirthDate();
}