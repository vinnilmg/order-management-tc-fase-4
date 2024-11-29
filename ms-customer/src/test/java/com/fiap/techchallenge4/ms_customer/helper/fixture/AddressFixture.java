package com.fiap.techchallenge4.ms_customer.helper.fixture;

import com.fiap.techchallenge4.ms_customer.domain.entities.address.Address;
import com.fiap.techchallenge4.ms_customer.domain.entities.address.AddressDomain;

import java.util.List;

public class AddressFixture {
    public static List<Address> ADDRESSES() {
        return List.of(NEW_ADDRESS());
    }

    public static Address NEW_ADDRESS() {
        return AddressDomain.of(
                1L,
                "Rua Regina Helena Faustino Carvalho",
                "33",
                null,
                "Vila Santa Rita",
                "Itapevi",
                "SP",
                "06660570"
        );
    }
}
