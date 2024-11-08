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

class ProcessedOrderProducerTest {
    private ProcessedOrderProducer processedOrderProducer;
    private KafkaTemplate<String, Serializable> kafkaTemplate;
    private String topicName;

    @BeforeEach
    void init() {
        kafkaTemplate = mock(KafkaTemplate.class);
        topicName = "test-topic";
        processedOrderProducer = new ProcessedOrderProducer(kafkaTemplate);
        ReflectionTestUtils.setField(processedOrderProducer, "topicName", topicName);
    }

    @Test
    void shouldProduceProcessedOrderMessage() {
        final var order = mock(Order.class);

        when(kafkaTemplate.send(topicName, order))
                .thenReturn(mock(CompletableFuture.class));

        processedOrderProducer.create(order);

        verify(kafkaTemplate).send(topicName, order);
    }
}
