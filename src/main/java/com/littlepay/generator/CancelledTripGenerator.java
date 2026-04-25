package com.littlepay.generator;

import com.littlepay.model.TapRecord;
import com.littlepay.model.Trip;

import java.math.BigDecimal;

import static com.littlepay.model.TripStatus.CANCELLED;


public class CancelledTripGenerator implements TripGenerator {

    @Override
    public Trip generate(TapRecord start, TapRecord end) {
        return new Trip(
                start.tapAt(),
                end.tapAt(),
                calculateDuration(start, end),
                start.stopId(),
                end.stopId(),
                BigDecimal.ZERO,
                start.companyId(),
                start.busId(),
                start.pan(),
                CANCELLED
        );
    }
}
