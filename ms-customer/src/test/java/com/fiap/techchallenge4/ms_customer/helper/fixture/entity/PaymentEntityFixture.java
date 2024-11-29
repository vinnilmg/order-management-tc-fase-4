package com.fiap.techchallenge4.ms_customer.helper.fixture.entity;

import com.fiap.techchallenge4.ms_customer.infra.persistence.entities.PaymentEntity;

import java.time.LocalDate;

public class PaymentEntityFixture {
    public static PaymentEntity CREATED() {
        final var result = new PaymentEntity();
        result.setId(1L);
        result.setCardNumber("5156337745346657");
        result.setCvvCode("486");
        result.setExpirationDate(LocalDate.of(2025, 4, 25));
        return result;
    }
}
