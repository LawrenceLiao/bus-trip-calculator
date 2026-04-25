package com.littlepay.service;

import com.littlepay.calculator.TripCalculator;
import com.littlepay.model.TapRecord;
import com.littlepay.model.Trip;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class TripProcessService {
    private final TripCalculator calculator;

    public List<Trip> processTrips(List<TapRecord> tapRecords) {
        if (tapRecords == null || tapRecords.isEmpty()) {
            throw new IllegalArgumentException("Empty input collection");
        }

        return tapRecords.stream().map(calculator::calculateTrip)
                .filter(Objects::nonNull)
                .toList();
    }
}
