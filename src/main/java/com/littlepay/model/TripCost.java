package com.littlepay.model;

import java.math.BigDecimal;

public record TripCost(
        String startStop,
        String endStop,
        BigDecimal amount
) {
}
