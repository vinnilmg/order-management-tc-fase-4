package com.fiap.techchallenge4.ms_customer.infra.persistence.mappers;
import com.fiap.techchallenge4.ms_customer.domain.entities.payment.Payment;
import com.fiap.techchallenge4.ms_customer.helper.fixture.PaymentFixture;
import com.fiap.techchallenge4.ms_customer.helper.fixture.entity.PaymentEntityFixture;
import com.fiap.techchallenge4.ms_customer.infra.persistence.entities.PaymentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PaymentEntityMapperTest {
    private PaymentEntityMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new PaymentEntityMapperImpl();
    }

    @Test
    void shouldMapPaymentDomain() {
        final var paymentEntity = PaymentEntityFixture.CREATED();
        final var result = mapper.toDomain(paymentEntity);

        assertThat(result)
                .isNotNull()
                .extracting(
                        Payment::getId,
                        Payment::getCardNumber,
                        Payment::getCvvCode,
                        Payment::getExpirationDate
                ).containsExactly(
                        paymentEntity.getId(),
                        paymentEntity.getCardNumber(),
                        paymentEntity.getCvvCode(),
                        paymentEntity.getExpirationDate()
                );
    }

    @Test
    void shouldMapPaymentDomains() {
        final var paymentEntities = PaymentEntityFixture.PAYMENTS();
        final var result = mapper.toDomains(paymentEntities);

        assertThat(result)
                .isNotEmpty()
                .hasSize(paymentEntities.size());
    }

    @Test
    void shouldMapPaymentEntity() {
        final var payment = PaymentFixture.NEW_PAYMENT();
        final var result = mapper.toEntity(payment);

        assertThat(result)
                .isNotNull()
                .extracting(
                        PaymentEntity::getCardNumber,
                        PaymentEntity::getCvvCode,
                        PaymentEntity::getExpirationDate
                ).containsExactly(
                        payment.getCardNumber(),
                        payment.getCvvCode(),
                        payment.getExpirationDate()
                );
    }
}
