package com.littlepay.generator;

import com.littlepay.model.TapRecord;
import com.littlepay.model.Trip;

public interface TripGenerator {
    Trip generate(TapRecord start, TapRecord end);

    default long calculateDuration(TapRecord start, TapRecord end) {
        return end.tapAt().toEpochSecond() - start.tapAt().toEpochSecond();
    }
}
