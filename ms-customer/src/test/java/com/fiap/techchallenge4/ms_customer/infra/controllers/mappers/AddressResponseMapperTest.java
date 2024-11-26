package com.fiap.techchallenge4.ms_customer.infra.controllers.mappers;

import com.fiap.techchallenge4.ms_customer.helper.constants.CustomerConstants;
import com.fiap.techchallenge4.ms_customer.infra.controllers.response.AddressResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddressResponseMapperTest {
    private AddressResponseMapper addressResponseMapper;

    @BeforeEach
    void setup() {
        addressResponseMapper = new AddressResponseMapperImpl();
    }

    @Test
    void shouldMapAddressResponse() {
        final var address = CustomerConstants.DEFAULT_ADDRESS_DOMAIN;
        final var result = addressResponseMapper.toResponse(address);

        assertThat(result)
                .isNotNull()
                .extracting(
                        AddressResponse::id,
                        AddressResponse::street,
                        AddressResponse::number,
                        AddressResponse::district,
                        AddressResponse::city,
                        AddressResponse::state,
                        AddressResponse::cep
                ).containsExactly(
                        address.getId(),
                        address.getStreet(),
                        address.getNumber(),
                        address.getDistrict(),
                        address.getCity(),
                        address.getState(),
                        address.getCep());
    }
}
