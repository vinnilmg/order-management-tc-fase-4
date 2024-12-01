package com.fiap.techchallenge4.order.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FormatterUtilsTest {

    @Test
    void shouldThrowErrorWhenConstructClass() throws NoSuchMethodException {
        final var constructor = FormatterUtils.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertThatThrownBy(constructor::newInstance)
                .isInstanceOf(InvocationTargetException.class);
    }

    @Test
    void shouldFormatToCpf() {
        final var cpf = "47611017818";
        final var expected = "476.110.178-18";
        final var result = FormatterUtils.formatCpf(cpf);
        assertThat(result)
                .isNotNull()
                .isEqualTo(expected);
    }

    @Test
    void shouldFormatToRealBrasileiro() {
        final var value = new BigDecimal("10.33");
        final var result = FormatterUtils.formatRealBrasileiro(value);
        assertThat(result)
                .isNotNull()
                .contains(value.toString().replace(".", ","));
    }
}
