package com.fiap.techchallenge4.ms_customer.helper.fixture;

import com.fiap.techchallenge4.ms_customer.domain.entities.customer.Customer;
import com.fiap.techchallenge4.ms_customer.domain.entities.customer.CustomerDomain;

import static com.fiap.techchallenge4.ms_customer.helper.constants.CustomerConstants.*;
import java.util.List;

public class CustomerFixture {

    public static List<Customer> CUSTOMERS() {
        return List.of(NEW_CUSTOMER());
    }
    public static Customer NEW_CUSTOMER() {
        return CustomerDomain.of(
                1L,
                DEFAULT_CPF,
                DEFAULT_NAME,
                DEFAULT_ADDRESS_DOMAIN,
                DEFAULT_BIRTH_DATE,
                DEFAULT_PAYMENT_DOMAIN
        );
    }
}
