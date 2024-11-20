package com.fiap.techchallenge4.ms_customer.infra.persistence.repositories;

import com.fiap.techchallenge4.ms_customer.infra.persistence.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    Optional<AddressEntity> findAddressByCustomerCpf(String cpf);
}
