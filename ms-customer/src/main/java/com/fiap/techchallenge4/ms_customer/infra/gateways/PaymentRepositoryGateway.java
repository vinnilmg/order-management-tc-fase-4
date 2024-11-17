package com.fiap.techchallenge4.ms_customer.infra.gateways;

import com.fiap.techchallenge4.ms_customer.application.gateways.PaymentGateway;
import com.fiap.techchallenge4.ms_customer.domain.entities.payment.Payment;
import com.fiap.techchallenge4.ms_customer.infra.persistence.mappers.PaymentEntityMapper;
import com.fiap.techchallenge4.ms_customer.infra.persistence.repositories.PaymentRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PaymentRepositoryGateway implements PaymentGateway {
    private final PaymentRepository paymentRepository;
    private final PaymentEntityMapper paymentEntityMapper;

    public PaymentRepositoryGateway(PaymentRepository paymentRepository,
                                    PaymentEntityMapper paymentEntityMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentEntityMapper = paymentEntityMapper;
    }

    @Override
    public Payment create(final Payment payment) {
        final var entity = paymentEntityMapper.toEntity(payment);
        final var entitySaved = paymentRepository.save(entity);
        return paymentEntityMapper.toDomain(entitySaved);
    }

    @Override
    public List<Payment> findAllPayments() {
        final var entities = paymentRepository.findAll();
        return paymentEntityMapper.toDomains(entities);
    }

    @Override
    public Optional<Payment> findPaymentById(final Long id) {
        return paymentRepository.findById(id)
                .map(paymentEntityMapper::toDomain);
    }

    @Override
    public Optional<Payment> findPaymentByCustomerId(Long customerId) {
        return paymentRepository.findPaymentByCustomerId(customerId)
                .map(paymentEntityMapper::toDomain);
    }

    @Override
    public void update(final Payment payment) {
        final var entity = paymentEntityMapper.toEntity(payment);
        paymentRepository.save(entity);
    }

    @Override
    public void delete(final Long id) {
        paymentRepository.deleteById(id);
    }
}
