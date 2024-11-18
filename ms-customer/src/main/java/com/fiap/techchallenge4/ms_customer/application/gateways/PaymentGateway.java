package com.fiap.techchallenge4.ms_customer.application.gateways;

import com.fiap.techchallenge4.ms_customer.domain.entities.payment.Payment;
import java.util.List;
import java.util.Optional;

public interface PaymentGateway {
    Payment create(Payment payment);
    List<Payment> findAllPayments();
    Optional<Payment> findPaymentById(Long id);
    Optional<Payment> findPaymentByCustomerCpf(String cpf);
    void update(Payment payment);
    void delete(Long id);
}
