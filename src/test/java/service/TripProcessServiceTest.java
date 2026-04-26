package service;

import com.littlepay.calculator.TripCalculator;
import com.littlepay.model.TapRecord;
import com.littlepay.model.Trip;
import com.littlepay.service.TripProcessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static com.littlepay.model.TapType.OFF;
import static com.littlepay.model.TapType.ON;
import static com.littlepay.model.TripStatus.COMPLETED;
import static com.littlepay.model.TripStatus.INCOMPLETE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class TripProcessServiceTest {

    private TripProcessService service;

    private final TripCalculator calculator = Mockito.mock(TripCalculator.class);

    @BeforeEach
    void setUp() {
        service = new TripProcessService(calculator);
    }

    @Test
    void shouldThrowExceptionWhenGivenNullTripList() {
        assertThrows(IllegalArgumentException.class, () ->
                service.processTrips(null));
    }

    @Test
    void shouldThrowExceptionWhenGivenEmptyTripList() {
        assertThrows(IllegalArgumentException.class, () ->
                service.processTrips(List.of()));
    }

    @Test
    void shouldReturnTripListWithoutNullObjectWhenGivenValidTapList() {
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

        List<TapRecord> tapRecords = List.of(startTap, endTap, incompleteTap, singleTap);
        Trip completedTrip = generateCompletedTrip(startTap, endTap);
        Trip incompleteTrip = generateIncompleteTrip(incompleteTap);

        when(calculator.calculateTrip(startTap)).thenReturn(null);
        when(calculator.calculateTrip(endTap)).thenReturn(completedTrip);
        when(calculator.calculateTrip(singleTap)).thenReturn(incompleteTrip);

        List<Trip> expectedResult = List.of(completedTrip, incompleteTrip);

        List<Trip> result = service.processTrips(tapRecords);

        assertEquals(expectedResult, result);
    }

    private Trip generateCompletedTrip(TapRecord startTap, TapRecord endTap) {
        return Trip.builder()
                .started(startTap.tapAt())
                .finished(endTap.tapAt())
                .durationSecs(10000)
                .fromStopId(startTap.stopId())
                .toStopId(endTap.stopId())
                .chargeAmount(BigDecimal.ONE)
                .companyId(startTap.companyId())
                .busId(startTap.busId())
                .pan(startTap.pan())
                .status(COMPLETED)
                .build();
    }

    private Trip generateIncompleteTrip(TapRecord startTap) {
        return Trip.builder()
                .started(startTap.tapAt())
                .fromStopId(startTap.stopId())
                .chargeAmount(BigDecimal.TEN)
                .companyId(startTap.companyId())
                .busId(startTap.busId())
                .pan(startTap.pan())
                .status(INCOMPLETE)
                .build();
    }
}
