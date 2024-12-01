package com.fiap.techchallenge4.order.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateUtilsTest {

    @Test
    void shouldThrowErrorWhenConstructClass() throws NoSuchMethodException {
        final var constructor = DateUtils.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        assertThatThrownBy(constructor::newInstance)
                .isInstanceOf(InvocationTargetException.class);
    }

    @Test
    void shouldConvertToDate() {
        final var date = "1999-10-03";
        final var result = DateUtils.toDate(date);
        assertThat(result)
                .isNotNull()
                .hasToString(date);
    }
}
