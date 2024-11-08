package com.fiap.techchallenge4.order.domain.entities.customer.payment;

import java.time.LocalDate;

public interface Payment {
    String getCardNumber();

    LocalDate getExpirationDate();

    String getCode();
}
