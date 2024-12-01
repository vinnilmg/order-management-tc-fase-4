package com.fiap.techchallenge4.ms_customer.infra.gateways;

import com.fiap.techchallenge4.ms_customer.helper.fixture.PaymentFixture;
import com.fiap.techchallenge4.ms_customer.helper.fixture.entity.PaymentEntityFixture;
import com.fiap.techchallenge4.ms_customer.infra.persistence.mappers.PaymentEntityMapper;
import com.fiap.techchallenge4.ms_customer.infra.persistence.repositories.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static com.fiap.techchallenge4.ms_customer.helper.constants.CustomerConstants.DEFAULT_CPF;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentRepositoryGatewayTest {
    @InjectMocks
    private PaymentRepositoryGateway paymentRepositoryGateway;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentEntityMapper paymentEntityMapper;

    @Test
    void shouldFindAllPayments() {
        final var payments = List.of(PaymentEntityFixture.CREATED());
        final var expectedPayments = PaymentFixture.PAYMENTS();

        when(paymentRepository.findAll())
                .thenReturn(payments);

        when(paymentEntityMapper.toDomains(payments))
                .thenReturn(expectedPayments);

        final var result = paymentRepositoryGateway.findAllPayments();

        assertThat(result)
                .isNotEmpty()
                .hasSize(payments.size())
                .isEqualTo(expectedPayments);

        verify(paymentRepository).findAll();
        verify(paymentEntityMapper).toDomains(payments);
    }

    @Test
    void shouldFindPaymentById() {
        final var paymentId = 1L;
        final var paymentEntity = PaymentEntityFixture.CREATED();
        final var expectedPayment = PaymentFixture.NEW_PAYMENT();

        when(paymentRepository.findById(paymentId))
                .thenReturn(Optional.of(paymentEntity));

        when(paymentEntityMapper.toDomain(paymentEntity))
                .thenReturn(expectedPayment);

        final var result = paymentRepositoryGateway.findPaymentById(paymentId);

        assertThat(result)
                .isNotNull()
                .isPresent()
                .hasValue(expectedPayment);

        verify(paymentRepository).findById(paymentId);
        verify(paymentEntityMapper).toDomain(paymentEntity);
    }

    @Test
    void shouldFindPaymentByCustomerCpf() {
        final var cpf = DEFAULT_CPF;
        final var paymentEntity = PaymentEntityFixture.CREATED();
        final var expectedPayment = PaymentFixture.NEW_PAYMENT();

        when(paymentRepository.findPaymentByCustomerCpf(cpf))
                .thenReturn(Optional.of(paymentEntity));

        when(paymentEntityMapper.toDomain(paymentEntity))
                .thenReturn(expectedPayment);

        final var result = paymentRepositoryGateway.findPaymentByCustomerCpf(cpf);

        assertThat(result)
                .isNotNull()
                .isPresent()
                .hasValue(expectedPayment);

        verify(paymentRepository).findPaymentByCustomerCpf(cpf);
        verify(paymentEntityMapper).toDomain(paymentEntity);
    }

    @Test
    void shouldCreatePayment() {
        final var paymentEntity = PaymentEntityFixture.CREATED();
        final var payment = PaymentFixture.NEW_PAYMENT();

        when(paymentEntityMapper.toEntity(payment))
                .thenReturn(paymentEntity);

        when(paymentRepository.save(paymentEntity))
                .thenReturn(paymentEntity);

        when(paymentEntityMapper.toDomain(paymentEntity))
                .thenReturn(payment);

        final var result = paymentRepositoryGateway.create(payment);

        assertThat(result)
                .isNotNull()
                .isEqualTo(payment);

        verify(paymentEntityMapper).toEntity(payment);
        verify(paymentRepository).save(paymentEntity);
        verify(paymentEntityMapper).toDomain(paymentEntity);
    }

    @Test
    void shouldUpdatePayment() {
        final var paymentEntity = PaymentEntityFixture.CREATED();
        final var payment = PaymentFixture.NEW_PAYMENT();

        when(paymentEntityMapper.toEntity(payment))
                .thenReturn(paymentEntity);

        paymentRepositoryGateway.update(payment);

        verify(paymentEntityMapper).toEntity(payment);
        verify(paymentRepository).save(paymentEntity);
    }

    @Test
    void shouldDeletePayment() {
        final var paymentId = 1L;

        paymentRepositoryGateway.delete(paymentId);

        verify(paymentRepository).deleteById(paymentId);
    }
}
