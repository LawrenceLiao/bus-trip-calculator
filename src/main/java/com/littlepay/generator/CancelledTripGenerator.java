package com.littlepay.generator;

import com.littlepay.model.TapRecord;
import com.littlepay.model.Trip;

import java.math.BigDecimal;

import static com.littlepay.model.TripStatus.CANCELLED;


public class CancelledTripGenerator implements TripGenerator {

    @Override
    public Trip generate(TapRecord start, TapRecord end) {
        return Trip.builder()
                .started(start.tapAt())
                .finished(end.tapAt())
                .durationSecs(calculateDuration(start, end))
                .fromStopId(start.stopId())
                .toStopId(end.stopId())
                .chargeAmount(BigDecimal.ZERO)
                .companyId(start.companyId())
                .busId(start.busId())
                .pan(start.pan())
                .status(CANCELLED)
                .build();
    }
}
