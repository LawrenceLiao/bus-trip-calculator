package com.littlepay.constant;

import com.littlepay.model.TripCost;

import java.math.BigDecimal;
import java.util.Map;

public class TripCosts {
    private static final String STOP_1 = "Stop1";
    private static final String STOP_2 = "Stop2";
    private static final String STOP_3 = "Stop3";

    private static final Map<String, Map<String, TripCost>> tripCostMap = Map.of(
            STOP_1, Map.of(
                    STOP_2, new TripCost(STOP_1, STOP_2, BigDecimal.valueOf(3.25)),
                    STOP_3, new TripCost(STOP_1, STOP_3, BigDecimal.valueOf(7.3))
            ),
            STOP_2, Map.of(
                    STOP_1, new TripCost(STOP_2, STOP_1, BigDecimal.valueOf(3.25)),
                    STOP_3, new TripCost(STOP_2, STOP_3, BigDecimal.valueOf(5.5))
            ),
            STOP_3, Map.of(
                    STOP_2, new TripCost(STOP_3, STOP_2,  BigDecimal.valueOf(5.5)),
                    STOP_1, new TripCost(STOP_3, STOP_1, BigDecimal.valueOf(7.3))
            )
    );

    public Map<String, Map<String, TripCost>> getTripCostMap() {
        return tripCostMap;
    }
}
