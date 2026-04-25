package com.littlepay.model;

import java.time.OffsetDateTime;

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
