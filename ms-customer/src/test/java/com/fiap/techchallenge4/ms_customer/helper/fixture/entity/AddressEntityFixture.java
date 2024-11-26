package com.fiap.techchallenge4.ms_customer.helper.fixture.entity;

import com.fiap.techchallenge4.ms_customer.infra.persistence.entities.AddressEntity;

public class AddressEntityFixture {
    public static AddressEntity CREATED() {
        final var result = new AddressEntity();
        result.setId(1L);
        result.setStreet("Rua Joaquim Cesário de Lima");
        result.setNumber("2");
        result.setDistrict("Paraibinha");
        result.setCity("Picos");
        result.setStreet("PI");
        result.setCep("64606390");
        return result;
    }
}
