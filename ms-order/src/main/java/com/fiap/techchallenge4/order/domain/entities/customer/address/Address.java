package com.fiap.techchallenge4.order.domain.entities.customer.address;

public interface Address {
    Long getId();

    String getStreet();

    Integer getNumber();

    String getComplement();

    String getNeighborhood();

    String getCity();

    String getState();

    String getPostalCode();
}
