package com.littlepay.constant;

import com.littlepay.model.TripCost;

import java.math.BigDecimal;
import java.util.Map;

public class TripCosts {
    private static final Map<String, Map<String, TripCost>> tripCostMap = Map.of(
            "Stop 1", Map.of(
                    "Stop 2", new TripCost("Stop 1", "Stop 2",  BigDecimal.valueOf(3.25)),
                    "Stop 3", new TripCost("Stop 1", "Stop 3", BigDecimal.valueOf(7.3))
            ),
            "Stop 2", Map.of(
                    "Stop 1", new TripCost("Stop 2", "Stop 1", BigDecimal.valueOf(3.25)),
                    "Stop 3", new TripCost("Stop 2", "Stop 3", BigDecimal.valueOf(5.5))
            ),
            "Stop 3", Map.of(
                    "Stop 2", new TripCost("Stop 3", "Stop 2",  BigDecimal.valueOf(5.5)),
                    "Stop 1", new TripCost("Stop 3", "Stop 1", BigDecimal.valueOf(7.3))
            )
    );

    public Map<String, Map<String, TripCost>> getTripCostMap() {
        return tripCostMap;
    }
}
