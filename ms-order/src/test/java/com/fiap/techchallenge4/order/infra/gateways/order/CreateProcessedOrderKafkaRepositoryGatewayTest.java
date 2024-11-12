package com.fiap.techchallenge4.order.infra.gateways.order;

import com.fiap.techchallenge4.order.helper.fixture.domain.OrderDomainFixture;
import com.fiap.techchallenge4.order.infra.messaging.producer.ProcessedOrderProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateProcessedOrderKafkaRepositoryGatewayTest {
    @InjectMocks
    private CreateProcessedOrderKafkaRepositoryGateway createProcessedOrderKafkaRepositoryGateway;
    @Mock
    private ProcessedOrderProducer processedOrderProducer;

    @Test
    void shouldCreateProcessedOrderInKafka() {
        final var order = OrderDomainFixture.CRIADO();

        doNothing()
                .when(processedOrderProducer)
                .create(order);

        final var result = createProcessedOrderKafkaRepositoryGateway.create(order);
        assertThat(result)
                .isNotNull()
                .isEqualTo(order);

        verify(processedOrderProducer).create(order);
    }
}
