package com.fiap.techchallenge4.ms_customer.domain.entities.address;


public interface Address {
    Long getId();
    String getStreet();
    String getNumber();
    String getComplement();
    String getDistrict();
    String getCity();
    String getState();
    String getCep();
}
