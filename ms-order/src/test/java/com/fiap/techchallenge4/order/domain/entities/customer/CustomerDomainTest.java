package com.fiap.techchallenge4.order.domain.entities.customer;

import com.fiap.techchallenge4.order.helper.fixture.domain.AddressDomainFixture;
import org.junit.jupiter.api.Test;

import static com.fiap.techchallenge4.order.helper.constants.OrderConstants.CPF;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CustomerDomainTest {

    @Test
    void givenAnIdNullWhenConstructThenThrowNullPointerException() {
        assertThatThrownBy(() -> new CustomerDomain(
                null,
                null,
                null,
                null,
                null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Customer Id cannot be null");
    }

    @Test
    void givenACpfNullWhenConstructThenThrowNullPointerException() {
        assertThatThrownBy(() -> new CustomerDomain(
                1L,
                null,
                null,
                null,
                null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Customer CPF cannot be null");
    }

    @Test
    void givenANameNullWhenConstructThenThrowNullPointerException() {
        assertThatThrownBy(() -> new CustomerDomain(
                1L,
                CPF,
                null,
                null,
                null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Customer Name cannot be null");
    }

    @Test
    void givenABirthDateNullWhenConstructThenThrowNullPointerException() {
        assertThatThrownBy(() -> new CustomerDomain(
                1L,
                CPF,
                "Vinicius",
                null,
                null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Customer Birth Date cannot be null");
    }

    @Test
    void givenAnAddressNullWhenConstructThenThrowNullPointerException() {
        assertThatThrownBy(() -> new CustomerDomain(
                1L,
                CPF,
                "Vinicius",
                "1998-11-03",
                null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("Customer Address cannot be null");
    }

    @Test
    void shouldConstructCustomerDomain() {
        final var id = 1L;
        final var cpf = CPF;
        final var name = "Ronaldo";
        final var birthDate = "2000-01-01";
        final var address = AddressDomainFixture.FULL();
        final var result = new CustomerDomain(
                id,
                cpf,
                name,
                birthDate,
                address
        );

        assertThat(result)
                .isNotNull()
                .extracting(
                        Customer::getId,
                        Customer::getCpf,
                        Customer::getName,
                        Customer::getBirthDate,
                        Customer::getAddress
                ).containsExactly(
                        id,
                        cpf,
                        name,
                        birthDate,
                        address
                );
    }
}
