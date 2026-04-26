package generator;

import com.littlepay.generator.CancelledTripGenerator;
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

public class CancelledTripGeneratorTest {

    private CancelledTripGenerator cancelledTripGenerator;

    @BeforeEach
    void setUp() {
        cancelledTripGenerator = new CancelledTripGenerator();
    }

    @Test
    void shouldGenerateCancelledTripRecord() {
        TapRecord onTap = TapRecord.builder()
                .id(1)
                .tapAt(OffsetDateTime.now())
                .tapType(ON)
                .busId("Bus1")
                .companyId("Company1")
                .stopId("Stop1")
                .pan("11111")
                .build();

        TapRecord offTap = TapRecord.builder()
                .id(2)
                .tapAt(OffsetDateTime.now().plusMinutes(5))
                .tapType(OFF)
                .busId("Bus1")
                .companyId("Company1")
                .stopId("Stop1")
                .pan("11111")
                .build();

        Trip result = cancelledTripGenerator.generate(onTap, offTap);

        assertEquals(onTap.busId(), result.busId());
        assertEquals(onTap.companyId(), result.companyId());
        assertEquals(onTap.stopId(), result.fromStopId());
        assertEquals(onTap.stopId(), result.toStopId());
        assertEquals(onTap.pan(), result.pan());
        assertEquals(BigDecimal.ZERO, result.chargeAmount());
        assertEquals(TripStatus.CANCELLED, result.status());
        assertEquals(300, result.durationSecs());

    }
}
