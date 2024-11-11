package com.fiap.techchallenge4.ms_customer.infra.persistence.repositories;

import com.fiap.techchallenge4.ms_customer.infra.persistence.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
