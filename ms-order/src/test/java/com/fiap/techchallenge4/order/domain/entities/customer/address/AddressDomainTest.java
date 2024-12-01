package com.fiap.techchallenge4.order.domain.entities.customer.address;

import org.junit.jupiter.api.Test;

import static com.fiap.techchallenge4.order.helper.constants.AddressConstants.POSTAL_CODE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AddressDomainTest {

    @Test
    void givenANullIdWhenConstructThenThrowNullPointerException() {
        assertThatThrownBy(() -> new AddressDomain(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Address Id cannot be null");
    }

    @Test
    void givenANullStreetWhenConstructThenThrowNullPointerException() {
        assertThatThrownBy(() -> new AddressDomain(
                1L,
                null,
                null,
                null,
                null,
                null,
                null,
                null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Address Street cannot be null");
    }

    @Test
    void givenANullNumberWhenConstructThenThrowNullPointerException() {
        assertThatThrownBy(() -> new AddressDomain(
                1L,
                "Rua dos nulos",
                null,
                null,
                null,
                null,
                null,
                null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Address Number cannot be null");
    }

    @Test
    void givenANullNeighborhoodWhenConstructThenThrowNullPointerException() {
        assertThatThrownBy(() -> new AddressDomain(
                1L,
                "Rua dos nulos",
                1,
                null,
                null,
                null,
                null,
                null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Address Neighborhood cannot be null");
    }

    @Test
    void givenANullCityWhenConstructThenThrowNullPointerException() {
        assertThatThrownBy(() -> new AddressDomain(
                1L,
                "Rua dos nulos",
                1,
                null,
                "Bairro de TI",
                null,
                null,
                null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Address City cannot be null");
    }

    @Test
    void givenANullStateWhenConstructThenThrowNullPointerException() {
        assertThatThrownBy(() -> new AddressDomain(
                1L,
                "Rua dos nulos",
                1,
                null,
                "Bairro de TI",
                "Cidade maluca",
                null,
                null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Address State cannot be null");
    }

    @Test
    void givenANullPostalCodeWhenConstructThenThrowNullPointerException() {
        assertThatThrownBy(() -> new AddressDomain(
                1L,
                "Rua dos nulos",
                1,
                null,
                "Bairro de TI",
                "Cidade maluca",
                "SP",
                null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Address Postal Code cannot be null");
    }

    @Test
    void shouldConstructAddressDomain() {
        final var id = 1L;
        final var street = "Rua dos malucos";
        final var number = 5;
        final var complement = "CS 2";
        final var neighborhood = "Vila Ramos";
        final var city = "São Paulo";
        final var state = "SP";
        final var postalCode = POSTAL_CODE;
        final var result = new AddressDomain(
                id,
                street,
                number,
                complement,
                neighborhood,
                city,
                state,
                postalCode
        );

        assertThat(result)
                .isNotNull()
                .extracting(
                        Address::getId,
                        Address::getStreet,
                        Address::getNumber,
                        Address::getComplement,
                        Address::getNeighborhood,
                        Address::getCity,
                        Address::getState,
                        Address::getPostalCode
                ).containsExactly(
                        id,
                        street,
                        number,
                        complement,
                        neighborhood,
                        city,
                        state,
                        postalCode
                );
    }
}
