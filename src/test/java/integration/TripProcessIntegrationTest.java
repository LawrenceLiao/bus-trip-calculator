package integration;

import com.littlepay.calculator.TripCalculator;
import com.littlepay.model.TapRecord;
import com.littlepay.model.Trip;
import com.littlepay.service.TripGeneratorFactory;
import com.littlepay.service.TripProcessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.List;

import static com.littlepay.model.TapType.OFF;
import static com.littlepay.model.TapType.ON;
import static com.littlepay.model.TripStatus.CANCELLED;
import static com.littlepay.model.TripStatus.COMPLETED;
import static com.littlepay.model.TripStatus.INCOMPLETE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TripProcessIntegrationTest {

    private TripProcessService tripProcessService;

    @BeforeEach
    void setUp() {
        tripProcessService = new TripProcessService(new TripCalculator(new TripGeneratorFactory()));
    }

    @Test
    void shouldProcessTripAccordingToGivenTapRecords() {
        TapRecord startTap = TapRecord.builder()
                .id(1)
                .tapAt(OffsetDateTime.now())
                .tapType(ON)
                .busId("Bus1")
                .companyId("Company1")
                .stopId("Stop1")
                .pan("11111")
                .build();

        TapRecord endTap = TapRecord.builder()
                .id(2)
                .tapAt(OffsetDateTime.now().plusMinutes(10))
                .tapType(OFF)
                .busId("Bus1")
                .companyId("Company1")
                .stopId("Stop3")
                .pan("11111")
                .build();

        TapRecord incompleteTap = TapRecord.builder()
                .id(3)
                .tapAt(OffsetDateTime.now().plusMinutes(5))
                .tapType(ON)
                .busId("Bus2")
                .companyId("Company2")
                .stopId("Stop3")
                .pan("22222")
                .build();

        TapRecord singleTap = TapRecord.builder()
                .id(4)
                .tapAt(OffsetDateTime.now().plusMinutes(15))
                .tapType(ON)
                .busId("Bus3")
                .companyId("Company2")
                .stopId("Stop1")
                .pan("22222")
                .build();

        TapRecord cancelledStartTap = TapRecord.builder()
                .id(5)
                .tapAt(OffsetDateTime.now().plusMinutes(20))
                .tapType(ON)
                .busId("Bus3")
                .companyId("Company2")
                .stopId("Stop1")
                .pan("11111")
                .build();

        TapRecord cancelledEndTap = TapRecord.builder()
                .id(5)
                .tapAt(OffsetDateTime.now().plusMinutes(25))
                .tapType(OFF)
                .busId("Bus3")
                .companyId("Company2")
                .stopId("Stop1")
                .pan("11111")
                .build();

        List<TapRecord> tapRecords = List.of(startTap, endTap, incompleteTap, singleTap, cancelledStartTap, cancelledEndTap);


        List<Trip> result = tripProcessService.processTrips(tapRecords);

        assertEquals(3, result.size());
        assertEquals(startTap.stopId(), result.get(0).fromStopId());
        assertEquals(endTap.stopId(), result.get(0).toStopId());
        assertEquals(startTap.tapAt(), result.get(0).started());
        assertEquals(endTap.tapAt(), result.get(0).finished());
        assertEquals(600, result.get(0).durationSecs());
        assertEquals(COMPLETED, result.get(0).status());
        assertEquals(startTap.pan(), result.get(0).pan());
        assertEquals(startTap.companyId(), result.get(0).companyId());
        assertEquals(startTap.busId(), result.get(0).busId());
        assertEquals(incompleteTap.stopId(), result.get(1).fromStopId());
        assertEquals("", result.get(1).toStopId());
        assertEquals(incompleteTap.tapAt(), result.get(1).started());
        assertNull(result.get(1).finished());
        assertEquals(0, result.get(1).durationSecs());
        assertEquals(INCOMPLETE, result.get(1).status());
        assertEquals(cancelledStartTap.stopId(), result.get(2).fromStopId());
        assertEquals(cancelledStartTap.stopId(), result.get(2).toStopId());
        assertEquals(cancelledStartTap.tapAt(), result.get(2).started());
        assertEquals(cancelledEndTap.tapAt(), result.get(2).finished());
        assertEquals(300, result.get(2).durationSecs());
        assertEquals(CANCELLED, result.get(2).status());
    }
}
