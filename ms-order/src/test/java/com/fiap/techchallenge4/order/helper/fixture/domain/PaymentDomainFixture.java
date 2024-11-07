package com.fiap.techchallenge4.order.helper.fixture.domain;

import com.fiap.techchallenge4.order.domain.entities.customer.payment.Payment;
import com.fiap.techchallenge4.order.domain.entities.customer.payment.PaymentDomain;

public class PaymentDomainFixture {

    public static Payment FULL() {
        return new PaymentDomain(
                "0001234567891234",
                "2030-01-01",
                "321"
        );
    }
}
