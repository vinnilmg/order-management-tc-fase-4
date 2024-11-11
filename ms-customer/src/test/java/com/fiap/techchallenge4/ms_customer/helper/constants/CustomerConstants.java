package com.fiap.techchallenge4.ms_customer.helper.constants;

import com.fiap.techchallenge4.ms_customer.domain.entities.address.AddressDomain;
import com.fiap.techchallenge4.ms_customer.domain.entities.payment.PaymentDomain;

import java.time.LocalDate;

public class CustomerConstants {
    public static final String DEFAULT_CPF = "35202528051";
    public static final String DEFAULT_NAME = "Jon Pertwee";
    public static final AddressDomain DEFAULT_ADDRESS_DOMAIN =
            new AddressDomain(
                    "Rua São Lourenço",
                    "33",
                    null,
                    "Lagoa do Mato",
                    "Mossoró",
                    "RN",
                    "59604230"
            );
    public static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.of(1919, 7,7);
    public static final PaymentDomain DEFAULT_PAYMENT_DOMAIN =
            new PaymentDomain(
                    "5329694917211404",
                    LocalDate.of(2026, 10, 9),
                    "361"
            );
}
