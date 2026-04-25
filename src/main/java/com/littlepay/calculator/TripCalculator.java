package com.littlepay.calculator;

import com.littlepay.model.TapRecord;
import com.littlepay.model.Trip;
import com.littlepay.model.TripStatus;
import com.littlepay.service.TripGeneratorFactory;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import static com.littlepay.model.TapType.ON;
import static com.littlepay.model.TripStatus.CANCELLED;
import static com.littlepay.model.TripStatus.COMPLETED;
import static com.littlepay.model.TripStatus.INCOMPLETE;


@RequiredArgsConstructor
public class TripCalculator {
    private final Map<String, TapRecord> latestTaps;
    private final TripGeneratorFactory factory;

    public Trip calculateTrip(TapRecord tapRecord) {
        if (!latestTaps.containsKey(tapRecord.pan())) {
            latestTaps.put(tapRecord.pan(), tapRecord);
            return null;
        }
        TapRecord lastRecord = latestTaps.get(tapRecord.pan());
        TripStatus status = determineTripStatus(lastRecord, tapRecord);

        return factory.getGenerator(status).generate(lastRecord, tapRecord);
    }

    private TripStatus determineTripStatus(TapRecord lastRecord, TapRecord currentRecord) {
        if (currentRecord.tapType().equals(ON)) {
            latestTaps.put(currentRecord.pan(), currentRecord);
            return INCOMPLETE;
        }

        return isCancelledTrip(lastRecord, currentRecord) ? CANCELLED : COMPLETED;
    }

    private boolean isCancelledTrip(TapRecord lastRecord, TapRecord currentRecord) {
        return lastRecord.stopId().equals(currentRecord.stopId());
    }
}
