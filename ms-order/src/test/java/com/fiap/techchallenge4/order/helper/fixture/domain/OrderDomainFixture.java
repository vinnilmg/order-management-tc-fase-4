package com.fiap.techchallenge4.order.helper.fixture.domain;

import com.fiap.techchallenge4.order.domain.entities.order.Order;
import com.fiap.techchallenge4.order.domain.entities.order.OrderDomain;
import com.fiap.techchallenge4.order.domain.enums.OrderStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.fiap.techchallenge4.order.helper.constants.OrderConstants.CPF;

public class OrderDomainFixture {

    public static List<Order> ORDERS() {
        return List.of(CRIADO());
    }

    public static Order CRIADO() {
        return OrderDomain.of(
                1L,
                CPF,
                OrderStatusEnum.CRIADO.name(),
                new BigDecimal("50"),
                LocalDateTime.now(),
                null,
                List.of(ProductDomainFixture.FULL()),
                ShippingDomainFixture.FULL()
        );
    }

    public static Order EM_ROTA_DE_ENTREGA() {
        return OrderDomain.of(
                1L,
                CPF,
                OrderStatusEnum.EM_ROTA_DE_ENTREGA.name(),
                new BigDecimal("50"),
                LocalDateTime.now(),
                null,
                List.of(ProductDomainFixture.FULL()),
                ShippingDomainFixture.FULL()
        );
    }

    public static Order AGUARDANDO_ENVIO() {
        return OrderDomain.of(
                1L,
                CPF,
                OrderStatusEnum.AGUARDANDO_ENVIO.name(),
                new BigDecimal("50"),
                LocalDateTime.now(),
                null,
                List.of(ProductDomainFixture.FULL()),
                ShippingDomainFixture.FULL()
        );
    }

    public static Order PENDENTE_PAGAMENTO() {
        return OrderDomain.of(
                1L,
                CPF,
                OrderStatusEnum.PENDENTE_PAGAMENTO.name(),
                new BigDecimal("50"),
                LocalDateTime.now(),
                null,
                List.of(ProductDomainFixture.FULL()),
                ShippingDomainFixture.FULL()
        );
    }

    public static Order PROCESSADO() {
        return OrderDomain.of(
                1L,
                CPF,
                OrderStatusEnum.PROCESSADO.name(),
                new BigDecimal("50"),
                LocalDateTime.now(),
                null,
                List.of(ProductDomainFixture.FULL()),
                ShippingDomainFixture.FULL()
        );
    }
}
