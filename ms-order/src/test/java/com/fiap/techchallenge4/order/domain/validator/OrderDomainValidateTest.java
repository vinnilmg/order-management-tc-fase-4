package com.fiap.techchallenge4.order.domain.validator;

import com.fiap.techchallenge4.order.helper.fixture.domain.OrderDomainFixture;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderDomainValidateTest {

    @Test
    void shouldReturnTrueWhenOrderIsValid() {
        final var order = OrderDomainFixture.CRIADO();
        final var result = OrderDomainValidate.validate(order);
        assertThat(result)
                .isTrue();
    }
}
