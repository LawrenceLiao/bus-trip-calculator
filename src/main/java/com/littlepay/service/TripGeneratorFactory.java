package com.littlepay.service;

import com.littlepay.config.TripFares;
import com.littlepay.generator.CancelledTripGenerator;
import com.littlepay.generator.CompletedTripGenerator;
import com.littlepay.generator.IncompleteTripGenerator;
import com.littlepay.generator.TripGenerator;

import java.util.Map;

import static com.littlepay.model.TripStatus.CANCELLED;
import static com.littlepay.model.TripStatus.COMPLETED;
import static com.littlepay.model.TripStatus.INCOMPLETE;

public class TripGeneratorFactory {
    private final Map<String, TripGenerator> tripGenerators;

    public TripGeneratorFactory() {
        tripGenerators = Map.of(
                COMPLETED.status, new CompletedTripGenerator(TripFares.tripFareMap),
                INCOMPLETE.status, new IncompleteTripGenerator(TripFares.tripFareMap),
                CANCELLED.status, new CancelledTripGenerator()
        );
    }

    public TripGenerator getGenerator(String status) {
        if (!tripGenerators.containsKey(status)) {
            throw new RuntimeException("No trip generator relevant to given status: " + status);
        }

        return tripGenerators.get(status);
    }
}
