package com.fiap.techchallenge4.order.domain.entities.customer;

import com.fiap.techchallenge4.order.domain.entities.customer.address.Address;

public interface Customer {
    Long getId();

    String getCpf();

    String getName();

    String getBirthDate();

    Address getAddress();
}
