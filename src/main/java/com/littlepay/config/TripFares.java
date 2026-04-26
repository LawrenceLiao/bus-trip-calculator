package com.littlepay.config;

import com.littlepay.model.TripFare;

import java.math.BigDecimal;
import java.util.Map;

public class TripFares {
    private static final String STOP_1 = "Stop1";
    private static final String STOP_2 = "Stop2";
    private static final String STOP_3 = "Stop3";

    public static final Map<String, Map<String, TripFare>> tripFareMap = Map.of(
            STOP_1, Map.of(
                    STOP_2, TripFare.builder().startStop(STOP_1).endStop(STOP_2).amount(BigDecimal.valueOf(3.25)).build(),
                    STOP_3, TripFare.builder().startStop(STOP_1).endStop(STOP_3).amount(BigDecimal.valueOf(7.3)).build()
            ),
            STOP_2, Map.of(
                    STOP_1, TripFare.builder().startStop(STOP_2).endStop(STOP_1).amount(BigDecimal.valueOf(3.25)).build(),
                    STOP_3, TripFare.builder().startStop(STOP_2).endStop(STOP_3).amount(BigDecimal.valueOf(5.5)).build()
            ),
            STOP_3, Map.of(
                    STOP_2, TripFare.builder().startStop(STOP_3).endStop(STOP_2).amount(BigDecimal.valueOf(5.5)).build(),
                    STOP_1, TripFare.builder().startStop(STOP_3).endStop(STOP_1).amount(BigDecimal.valueOf(7.3)).build()
            )
    );
}
