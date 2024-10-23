package com.fiap.techchallenge4.order.infra.gateways.order;

import com.fiap.techchallenge4.order.application.gateways.order.CreateOrderGateway;
import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.infra.messaging.OrderPendingPaymentProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderKafkaRepositoryGateway implements CreateOrderGateway {
    private final OrderPendingPaymentProducer orderPendingPaymentProducer;

    public OrderKafkaRepositoryGateway(OrderPendingPaymentProducer orderPendingPaymentProducer) {
        this.orderPendingPaymentProducer = orderPendingPaymentProducer;
    }

    @Override
    public Order create(final Order order) {
        log.info("Postando mensagem no tópico de pedido com pagamento pendente...");
        orderPendingPaymentProducer.create(order);
        return order;
    }
}
