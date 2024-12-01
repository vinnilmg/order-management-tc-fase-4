package com.fiap.techchallenge4.ms_customer.domain.entities.payment;

import java.time.LocalDate;

public interface Payment {
    Long getId();
    String getCardNumber();
    LocalDate getExpirationDate();
    String getCvvCode();
}
