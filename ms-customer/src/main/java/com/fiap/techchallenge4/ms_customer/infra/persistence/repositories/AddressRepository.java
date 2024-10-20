package com.fiap.techchallenge4.ms_customer.infra.persistence.repositories;

import com.fiap.techchallenge4.ms_customer.infra.persistence.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
