package com.fiap.techchallenge4.order.utils;

import javax.swing.text.MaskFormatter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class FormatterUtils {
    private FormatterUtils() {
        throw new IllegalArgumentException("Class cannot be instancied");
    }

    private static final String CPF_MASK = "###.###.###-##";

    public static String formatCpf(final String cpf) {
        return format(cpf, CPF_MASK);
    }

    public static String format(final String value, final String mask) {
        MaskFormatter mf;
        try {
            mf = new MaskFormatter(mask);
            mf.setValueContainsLiteralCharacters(false);
            return mf.valueToString(value);
        } catch (ParseException ex) {
            return value;
        }
    }

    public static String formatRealBrasileiro(final BigDecimal value) {
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR"))
                .format(value);
    }
}
