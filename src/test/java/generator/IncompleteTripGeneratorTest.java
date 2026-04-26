package generator;

import com.littlepay.config.TripFares;
import com.littlepay.generator.IncompleteTripGenerator;
import com.littlepay.model.TapRecord;
import com.littlepay.model.Trip;
import com.littlepay.model.TripFare;
import com.littlepay.model.TripStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static com.littlepay.model.TapType.ON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class IncompleteTripGeneratorTest {
    private IncompleteTripGenerator incompleteTripGenerator;

    @BeforeEach
    void setUp() {
        incompleteTripGenerator = new IncompleteTripGenerator(TripFares.tripFareMap);
    }

    @Test
    void shouldGenerateIncompleteTripRecordWithCalculatedDurationSecsWhenGivenOnlyOneOnTap() {
        TapRecord firstTap = TapRecord.builder()
                .id(1)
                .tapAt(OffsetDateTime.now())
                .tapType(ON)
                .busId("Bus1")
                .companyId("Company1")
                .stopId("Stop1")
                .pan("11111")
                .build();

        TapRecord secondTap = TapRecord.builder()
                .id(2)
                .tapAt(OffsetDateTime.now().plusMinutes(6))
                .tapType(ON)
                .busId("Bus2")
                .companyId("Company2")
                .stopId("Stop2")
                .pan("11111")
                .build();

        Trip result = incompleteTripGenerator.generate(firstTap, secondTap);

        BigDecimal expectedChargeAmount = TripFares.tripFareMap.get(firstTap.stopId()).values().stream()
                .map(TripFare::amount)
                                .max(BigDecimal::compareTo)
                                        .orElse(BigDecimal.ZERO);

        assertEquals(firstTap.busId(), result.busId());
        assertEquals(firstTap.companyId(), result.companyId());
        assertEquals(firstTap.stopId(), result.fromStopId());
        assertEquals("", result.toStopId());
        assertEquals(firstTap.pan(), result.pan());
        assertEquals(expectedChargeAmount, result.chargeAmount());
        assertEquals(TripStatus.INCOMPLETE, result.status());
        assertEquals(0, result.durationSecs());
        assertEquals(firstTap.tapAt(), result.started());
        assertNull(result.finished());
    }
}
