package com.littlepay.generator;

import com.littlepay.constant.TripCosts;
import com.littlepay.model.TapRecord;
import com.littlepay.model.Trip;
import com.littlepay.model.TripCost;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import static com.littlepay.model.TripStatus.INCOMPLETE;

@RequiredArgsConstructor
public class IncompleteTripGenerator implements TripGenerator {

    private static final String EMPTY_STRING = "";

    private final TripCosts tripCosts;

    @Override
    public Trip generate(TapRecord start, TapRecord end) {
        BigDecimal chargeAmount = calculateChargeAmount(start.stopId());

        return Trip.builder()
                .started(start.tapAt())
                .finished(end.tapAt())
                .durationSecs(calculateDuration(start, end))
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
        return tripCosts.getTripCostMap().get(onStop).values()
                .stream()
                .map(TripCost::amount)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
    }
}
