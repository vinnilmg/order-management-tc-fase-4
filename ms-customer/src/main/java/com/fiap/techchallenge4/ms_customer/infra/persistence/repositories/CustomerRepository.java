package com.fiap.techchallenge4.ms_customer.infra.persistence.repositories;

import com.fiap.techchallenge4.ms_customer.infra.persistence.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByCpf(String cpf);
}
