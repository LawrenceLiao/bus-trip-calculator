package com.littlepay.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static com.littlepay.helper.DateTimeHelper.formatDate;

@Builder
public record Trip(
        OffsetDateTime started,
        OffsetDateTime finished,
        long durationSecs,
        String fromStopId,
        String toStopId,
        BigDecimal chargeAmount,
        String companyId,
        String busId,
        String pan,
        TripStatus status
) {
    @Override
    public String toString() {
        return formatDate(started) + ", " + formatDate(finished) + ", " + durationSecs + ", " + fromStopId + ", " + toStopId + ", $"
                + chargeAmount + ", " + companyId + ", " + busId + ", " + pan + ", " + status;
    }
}
