package com.littlepay.service;

import com.littlepay.constant.TripCosts;
import com.littlepay.generator.CancelledTripGenerator;
import com.littlepay.generator.CompletedTripGenerator;
import com.littlepay.generator.IncompleteTripGenerator;
import com.littlepay.generator.TripGenerator;
import com.littlepay.model.TripStatus;

import java.util.Map;

import static com.littlepay.model.TripStatus.CANCELLED;
import static com.littlepay.model.TripStatus.COMPLETED;
import static com.littlepay.model.TripStatus.INCOMPLETE;

public class TripGeneratorFactory {
    private final Map<TripStatus, TripGenerator> tripGenerators;

    public TripGeneratorFactory() {
        TripCosts tripCosts = new TripCosts();

        tripGenerators = Map.of(
                COMPLETED, new CompletedTripGenerator(tripCosts),
                INCOMPLETE, new IncompleteTripGenerator(tripCosts),
                CANCELLED, new CancelledTripGenerator()
        );
    }

    public TripGenerator getGenerator(TripStatus status) {
        if (!tripGenerators.containsKey(status)) {
            throw new RuntimeException("No trip generator relevant to given status");
        }

        return tripGenerators.get(status);
    }
}
