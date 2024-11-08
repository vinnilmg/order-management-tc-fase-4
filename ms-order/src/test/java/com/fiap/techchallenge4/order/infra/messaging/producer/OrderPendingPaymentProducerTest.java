package com.fiap.techchallenge4.order.infra.messaging.producer;

import com.fiap.techchallenge4.order.domain.entities.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderPendingPaymentProducerTest {
    private OrderPendingPaymentProducer orderPendingPaymentProducer;
    private KafkaTemplate<String, Serializable> kafkaTemplate;
    private String topicName;

    @BeforeEach
    void init() {
        kafkaTemplate = mock(KafkaTemplate.class);
        topicName = "test-topic";
        orderPendingPaymentProducer = new OrderPendingPaymentProducer(kafkaTemplate);
        ReflectionTestUtils.setField(orderPendingPaymentProducer, "topicName", topicName);
    }

    @Test
    void shouldProduceOrderPendingPaymentMessage() {
        final var order = mock(Order.class);

        when(kafkaTemplate.send(topicName, order))
                .thenReturn(mock(CompletableFuture.class));

        orderPendingPaymentProducer.create(order);

        verify(kafkaTemplate).send(topicName, order);
    }
}
