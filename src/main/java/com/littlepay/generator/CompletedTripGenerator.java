package com.littlepay.generator;

import com.littlepay.constant.TripCosts;
import com.littlepay.model.TapRecord;
import com.littlepay.model.Trip;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import static com.littlepay.model.TripStatus.COMPLETED;

@RequiredArgsConstructor
public class CompletedTripGenerator implements TripGenerator {

    private final TripCosts tripCosts;

    @Override
    public Trip generate(TapRecord start, TapRecord end) {

        BigDecimal chargeAmount = calculateChargeAmount(start.stopId(), end.stopId());

        return new Trip(
                start.tapAt(),
                end.tapAt(),
                calculateDuration(start, end),
                start.stopId(),
                end.stopId(),
                chargeAmount,
                start.companyId(),
                start.busId(),
                start.pan(),
                COMPLETED
        );
    }

    private BigDecimal calculateChargeAmount(String onStop, String offStop) {
        return tripCosts.getTripCostMap().get(onStop).get(offStop).amount();
    }
}
