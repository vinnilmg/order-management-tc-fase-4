package com.fiap.techchallenge4.ms_customer.helper.fixture.request;

import com.fiap.techchallenge4.ms_customer.infra.controllers.request.CustomerRequest;

import static com.fiap.techchallenge4.ms_customer.helper.constants.CustomerConstants.*;

public class CustomerRequestFixture {
    public static CustomerRequest FULL() {
        return new CustomerRequest(
                DEFAULT_CPF,
                DEFAULT_NAME,
                AddressRequestFixture.FULL(),
                DEFAULT_BIRTH_DATE.toString(),
                PaymentRequestFixture.FULL()
        );
    }
}
