package com.littlepay.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyHelper {
    private static final String DOLLAR_LABEL = "$";
    private static final String DEFAULT_AMOUNT = "$0.00";

    public static String formatMoney(BigDecimal amount) {
        if (amount == null) return DEFAULT_AMOUNT;
        return DOLLAR_LABEL + amount.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }
}
