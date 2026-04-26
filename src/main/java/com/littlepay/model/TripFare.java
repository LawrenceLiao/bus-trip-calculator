package com.littlepay.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TripFare(
        String startStop,
        String endStop,
        BigDecimal amount
) {
}
