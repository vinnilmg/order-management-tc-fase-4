package com.fiap.techchallenge4.ms_customer.helper.fixture;

import com.fiap.techchallenge4.ms_customer.domain.entities.payment.Payment;
import com.fiap.techchallenge4.ms_customer.domain.entities.payment.PaymentDomain;

import java.time.LocalDate;
import java.util.List;

public class PaymentFixture {
    public static List<Payment>  PAYMENTS() {
        return List.of(NEW_PAYMENT());
    }

    public static Payment NEW_PAYMENT() {
        return PaymentDomain.of(
                1L,
                "5322184664286848",
                LocalDate.of(2026,7,25),
                "786"
        );
    }
}
