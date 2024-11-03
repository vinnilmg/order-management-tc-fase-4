package com.fiap.techchallenge4.order.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private DateUtils() {
        throw new IllegalArgumentException("Class cannot be instancied");
    }

    public static LocalDate toDate(final String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
