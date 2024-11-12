package com.fiap.techchallenge4.order.infra.gateways.order;

import com.fiap.techchallenge4.order.helper.fixture.domain.OrderDomainFixture;
import com.fiap.techchallenge4.order.infra.messaging.producer.CreatedOrderProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateOrderKafkaRepositoryGatewayTest {
    @InjectMocks
    private CreateOrderKafkaRepositoryGateway createOrderKafkaRepositoryGateway;
    @Mock
    private CreatedOrderProducer createdOrderProducer;

    @Test
    void shouldCreateCreatedOrderInKafka() {
        final var order = OrderDomainFixture.CRIADO();

        doNothing()
                .when(createdOrderProducer)
                .create(order);

        final var result = createOrderKafkaRepositoryGateway.create(order);
        assertThat(result)
                .isNotNull()
                .isEqualTo(order);

        verify(createdOrderProducer).create(order);
    }
}
