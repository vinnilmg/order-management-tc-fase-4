package com.fiap.techchallenge4.order.infra.persistence.repositories;

import com.fiap.techchallenge4.order.infra.persistence.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByCpf(String cpf);

    @Transactional
    @Modifying
    @Query("update OrderEntity set status = :status where id = :id")
    void updateStatus(@Param(value = "id") Long id, @Param(value = "status") String status);
}
