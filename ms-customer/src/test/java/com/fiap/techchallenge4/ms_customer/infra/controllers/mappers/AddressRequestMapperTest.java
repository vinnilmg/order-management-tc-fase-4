package com.fiap.techchallenge4.ms_customer.infra.controllers.mappers;

import com.fiap.techchallenge4.ms_customer.domain.entities.address.Address;
import com.fiap.techchallenge4.ms_customer.helper.fixture.request.AddressRequestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddressRequestMapperTest {
    private AddressRequestMapper addressRequestMapper;

    @BeforeEach
    void setup() {
        addressRequestMapper = new AddressRequestMapperImpl();
    }

    @Test
    void shouldMapAddressRequest() {
        final var addressRequest = AddressRequestFixture.FULL();

        final var result = addressRequestMapper.toAddress(addressRequest);

        assertThat(result)
                .isNotNull()
                .extracting(Address::getStreet,
                        Address::getNumber,
                        Address::getDistrict,
                        Address::getCity,
                        Address::getState,
                        Address::getCep
                ).containsExactly(
                        addressRequest.street(),
                        addressRequest.number(),
                        addressRequest.district(),
                        addressRequest.city(),
                        addressRequest.state(),
                        addressRequest.cep());
    }
}
