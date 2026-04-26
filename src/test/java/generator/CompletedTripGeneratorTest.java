package generator;

import com.littlepay.config.TripFares;
import com.littlepay.generator.CompletedTripGenerator;
import com.littlepay.model.TapRecord;
import com.littlepay.model.Trip;
import com.littlepay.model.TripStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static com.littlepay.model.TapType.OFF;
import static com.littlepay.model.TapType.ON;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompletedTripGeneratorTest {

    private CompletedTripGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new CompletedTripGenerator(TripFares.tripFareMap);
    }

    @Test
    void shouldGenerateCompletedTripRecord() {
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
                .tapAt(OffsetDateTime.now().plusMinutes(10))
                .tapType(OFF)
                .busId("Bus1")
                .companyId("Company1")
                .stopId("Stop3")
                .pan("11111")
                .build();

        Trip result = generator.generate(firstTap, secondTap);

        BigDecimal expectedChargeAmount = TripFares.tripFareMap.get(firstTap.stopId()).get(secondTap.stopId()).amount();

        assertEquals(firstTap.busId(), result.busId());
        assertEquals(firstTap.companyId(), result.companyId());
        assertEquals(firstTap.stopId(), result.fromStopId());
        assertEquals(secondTap.stopId(), result.toStopId());
        assertEquals(firstTap.pan(), result.pan());
        assertEquals(expectedChargeAmount, result.chargeAmount());
        assertEquals(TripStatus.COMPLETED, result.status());
        assertEquals(600, result.durationSecs());
        assertEquals(firstTap.tapAt(), result.started());
        assertEquals(secondTap.tapAt(), result.finished());
    }
}
