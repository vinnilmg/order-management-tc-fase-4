package com.fiap.techchallenge4.order.infra.gateways.order;

import com.fiap.techchallenge4.order.application.gateways.order.CreateOrderGateway;
import com.fiap.techchallenge4.order.domain.entities.order.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderKafkaRepositoryGateway implements CreateOrderGateway {
    @Value("${kafka.topic-name}")
    private String topicName;
    private final KafkaTemplate<String, Serializable> kafkaTemplate;

    @Override
    public Order create(final Order order) {
        log.info("Criando nova mensagem de pedido...");
        kafkaTemplate.send(topicName, order);
        return order;
    }
}
