package com.fiap.techchallenge4.ms_customer.helper.fixture.entity;

import com.fiap.techchallenge4.ms_customer.infra.persistence.entities.CustomerEntity;

import static com.fiap.techchallenge4.ms_customer.helper.constants.CustomerConstants.*;

public class CustomerEntityFixture {
    public static CustomerEntity CREATED() {
        final var result = new CustomerEntity();
        result.setId(1L);
        result.setCpf(DEFAULT_CPF);
        result.setName(DEFAULT_NAME);
        result.setAddress(AddressEntityFixture.CREATED());
        result.setBirthDate(DEFAULT_BIRTH_DATE);
        result.setPayment(PaymentEntityFixture.CREATED());
        return result;
    }
}
