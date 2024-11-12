package com.fiap.techchallenge4.order.helper.fixture.domain;

import com.fiap.techchallenge4.order.domain.entities.customer.CustomerDomain;

import static com.fiap.techchallenge4.order.helper.constants.OrderConstants.CPF;

public class CustomerDomainFixture {

    public static CustomerDomain FULL() {
        return new CustomerDomain(
                1L,
                CPF,
                "Ronaldinho Gaucho",
                "1999-10-03",
                AddressDomainFixture.FULL()
        );
    }
}
