package com.fiap.techchallenge4.order.infra.client.mappers;

import com.fiap.techchallenge4.order.domain.entities.customer.payment.Payment;
import com.fiap.techchallenge4.order.helper.fixture.response.ProviderPaymentInfoResponseFixture;
import com.fiap.techchallenge4.order.utils.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProviderPaymentInfoResponseMapperTest {
    private ProviderPaymentInfoResponseMapper mapper;

    @BeforeEach
    void init() {
        mapper = new ProviderPaymentInfoResponseMapperImpl();
    }

    @Test
    void shouldReturnNullWhenResponseIsNull() {
        final var result = mapper.toPaymentDomain(null);
        assertThat(result)
                .isNull();
    }

    @Test
    void shouldMapPaymentDomain() {
        final var response = ProviderPaymentInfoResponseFixture.FULL();
        final var result = mapper.toPaymentDomain(response);
        assertThat(result)
                .isNotNull()
                .extracting(
                        Payment::getCardNumber,
                        Payment::getCode,
                        Payment::getExpirationDate
                ).containsExactly(
                        response.cardNumber(),
                        response.code(),
                        DateUtils.toDate(response.expirationDate())
                );
    }
}
