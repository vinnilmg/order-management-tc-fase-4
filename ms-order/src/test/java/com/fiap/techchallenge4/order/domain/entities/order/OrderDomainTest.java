package com.fiap.techchallenge4.order.domain.entities.order;

import com.fiap.techchallenge4.order.domain.enums.OrderStatusEnum;
import com.fiap.techchallenge4.order.helper.fixture.domain.OrderDomainFixture;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderDomainTest {

    @Test
    void shouldUpdateStatusToPendingPayment() {
        final var order = OrderDomainFixture.CRIADO();
        assertThat(order.getStatusEnum())
                .isNotEqualTo(OrderStatusEnum.PENDENTE_PAGAMENTO);

        order.updateToPendingPayment();
        assertThat(order.getStatusEnum())
                .isEqualTo(OrderStatusEnum.PENDENTE_PAGAMENTO);
    }

    @Test
    void shouldUpdateStatusToProcessed() {
        final var order = OrderDomainFixture.CRIADO();
        assertThat(order.getStatusEnum())
                .isNotEqualTo(OrderStatusEnum.PROCESSADO);

        order.updateToProcessed();
        assertThat(order.getStatusEnum())
                .isEqualTo(OrderStatusEnum.PROCESSADO);
    }

    @Test
    void shouldUpdateStatusToWaitShipping() {
        final var order = OrderDomainFixture.CRIADO();
        assertThat(order.getStatusEnum())
                .isNotEqualTo(OrderStatusEnum.AGUARDANDO_ENVIO);

        order.updateToWaitShipping();
        assertThat(order.getStatusEnum())
                .isEqualTo(OrderStatusEnum.AGUARDANDO_ENVIO);
    }

    @Test
    void shouldUpdateStatusToCanceled() {
        final var order = OrderDomainFixture.CRIADO();
        assertThat(order.getStatusEnum())
                .isNotEqualTo(OrderStatusEnum.CANCELADO);

        order.updateToCanceled();
        assertThat(order.getStatusEnum())
                .isEqualTo(OrderStatusEnum.CANCELADO);
    }

    @Test
    void shouldUpdateStatusToDeliveryRoute() {
        final var order = OrderDomainFixture.CRIADO();
        assertThat(order.getStatusEnum())
                .isNotEqualTo(OrderStatusEnum.EM_ROTA_DE_ENTREGA);

        order.updateToDeliveryRoute();
        assertThat(order.getStatusEnum())
                .isEqualTo(OrderStatusEnum.EM_ROTA_DE_ENTREGA);
    }

    @Test
    void shouldFinishOrder() {
        final var order = OrderDomainFixture.CRIADO();
        assertThat(order.getStatusEnum())
                .isNotEqualTo(OrderStatusEnum.FINALIZADO);

        assertThat(order.getCompletionDate())
                .isNull();

        order.finish();
        assertThat(order.getStatusEnum())
                .isEqualTo(OrderStatusEnum.FINALIZADO);

        assertThat(order.getCompletionDate())
                .isNotNull();
    }
}
