package com.littlepay.generator;

import com.littlepay.model.TapRecord;
import com.littlepay.model.Trip;
import com.littlepay.model.TripFare;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

import static com.littlepay.model.TripStatus.INCOMPLETE;

@RequiredArgsConstructor
public class IncompleteTripGenerator implements TripGenerator {

    private static final String EMPTY_STRING = "";

    private final Map<String, Map<String, TripFare>> tripFares;

    @Override
    public Trip generate(TapRecord start, TapRecord end) {
        BigDecimal chargeAmount = calculateChargeAmount(start.stopId());

        return Trip.builder()
                .started(start.tapAt())
                .fromStopId(start.stopId())
                .toStopId(EMPTY_STRING)
                .chargeAmount(chargeAmount)
                .companyId(start.companyId())
                .busId(start.busId())
                .pan(start.pan())
                .status(INCOMPLETE)
                .build();
    }

    private BigDecimal calculateChargeAmount(String onStop) {
        return tripFares.get(onStop).values()
                .stream()
                .map(TripFare::amount)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }
}
