package com.littlepay.generator;

import com.littlepay.model.TapRecord;
import com.littlepay.model.Trip;
import com.littlepay.model.TripFare;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

import static com.littlepay.model.TripStatus.COMPLETED;

@RequiredArgsConstructor
public class CompletedTripGenerator implements TripGenerator {

    private final Map<String, Map<String, TripFare>> tripFares;

    @Override
    public Trip generate(TapRecord start, TapRecord end) {

        BigDecimal chargeAmount = calculateChargeAmount(start.stopId(), end.stopId());

        return Trip.builder()
                .started(start.tapAt())
                .finished(end.tapAt())
                .durationSecs(calculateDuration(start, end))
                .fromStopId(start.stopId())
                .toStopId(end.stopId())
                .chargeAmount(chargeAmount)
                .companyId(start.companyId())
                .busId(start.busId())
                .pan(start.pan())
                .status(COMPLETED)
                .build();
    }

    private BigDecimal calculateChargeAmount(String onStop, String offStop) {
        return tripFares.get(onStop).get(offStop).amount();
    }

}
