package com.fiap.techchallenge4.order.infra.gateways.order;

import com.fiap.techchallenge4.order.helper.fixture.domain.OrderDomainFixture;
import com.fiap.techchallenge4.order.infra.messaging.producer.OrderPendingPaymentProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateOrderPendingPaymentKafkaRepositoryGatewayTest {
    @InjectMocks
    private CreateOrderPendingPaymentKafkaRepositoryGateway createOrderPendingPaymentKafkaRepositoryGateway;
    @Mock
    private OrderPendingPaymentProducer orderPendingPaymentProducer;

    @Test
    void shouldCreateOrderPendingPaymentInKafka() {
        final var order = OrderDomainFixture.CRIADO();

        doNothing()
                .when(orderPendingPaymentProducer)
                .create(order);

        final var result = createOrderPendingPaymentKafkaRepositoryGateway.create(order);
        assertThat(result)
                .isNotNull()
                .isEqualTo(order);

        verify(orderPendingPaymentProducer).create(order);
    }
}
