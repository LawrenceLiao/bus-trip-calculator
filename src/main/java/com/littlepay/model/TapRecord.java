package com.littlepay.model;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record TapRecord(
        long id,
        OffsetDateTime tapAt,
        TapType tapType,
        String stopId,
        String companyId,
        String busId,
        String pan
) {
}
