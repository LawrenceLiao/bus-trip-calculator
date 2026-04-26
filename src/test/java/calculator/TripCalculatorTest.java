package calculator;

import com.littlepay.calculator.TripCalculator;
import com.littlepay.generator.CancelledTripGenerator;
import com.littlepay.generator.CompletedTripGenerator;
import com.littlepay.generator.IncompleteTripGenerator;
import com.littlepay.model.TapRecord;
import com.littlepay.model.Trip;
import com.littlepay.service.TripGeneratorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;

import static com.littlepay.model.TapType.OFF;
import static com.littlepay.model.TapType.ON;
import static com.littlepay.model.TripStatus.CANCELLED;
import static com.littlepay.model.TripStatus.COMPLETED;
import static com.littlepay.model.TripStatus.INCOMPLETE;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TripCalculatorTest {
    private TripCalculator calculator;
    private TripGeneratorFactory factory = mock(TripGeneratorFactory.class);
    private IncompleteTripGenerator incompleteTripGenerator = mock(IncompleteTripGenerator.class);
    private CompletedTripGenerator completedTripGenerator = mock(CompletedTripGenerator.class);
    private CancelledTripGenerator cancelledTripGenerator = mock(CancelledTripGenerator.class);

    @BeforeEach
    void setUp() {
        calculator = new TripCalculator(factory);
    }

    @Test
    void shouldReturnNullWhenGettingOnTapAndNoPreviousOnRecordStored() {
        TapRecord tapRecord = TapRecord.builder()
                .id(1)
                .tapAt(OffsetDateTime.now())
                .tapType(ON)
                .busId("Bus1")
                .companyId("Company1")
                .stopId("Stop1")
                .pan("11111")
                .build();

        Trip result = calculator.calculateTrip(tapRecord);

        assertNull(result);
    }

    @Test
    void shouldReturnNullWhenGettingOffTapAndNoMatchedOnRecordStored() {
        TapRecord tapRecord = TapRecord.builder()
                .id(1)
                .tapAt(OffsetDateTime.now())
                .tapType(OFF)
                .busId("Bus1")
                .companyId("Company1")
                .stopId("Stop1")
                .pan("11111")
                .build();
        Trip result = calculator.calculateTrip(tapRecord);

        assertNull(result);
    }

    @Test
    void shouldInvokeCancelledTripGeneratorWhenGettingOffTapWithSameBusStopWithMatchedOnRecord() {
        TapRecord onRecord = TapRecord.builder()
                .id(1)
                .tapAt(OffsetDateTime.now())
                .tapType(ON)
                .busId("Bus1")
                .companyId("Company1")
                .stopId("Stop1")
                .pan("11111")
                .build();
        Trip trip = calculator.calculateTrip(onRecord);
        assertNull(trip);

        TapRecord offRecord = TapRecord.builder()
                .id(2)
                .tapAt(OffsetDateTime.now().plusMinutes(10))
                .tapType(OFF)
                .busId("Bus1")
                .companyId("Company1")
                .stopId("Stop1")
                .pan("11111")
                .build();

        when(factory.getGenerator(eq(CANCELLED.status))).thenReturn(cancelledTripGenerator);
        calculator.calculateTrip(offRecord);
        verify(factory).getGenerator(eq(CANCELLED.status));
        verify(cancelledTripGenerator).generate(eq(onRecord), eq(offRecord));
    }

    @Test
    void shouldInvokeCompletedTripGeneratorWhenGettingOffTapWithDifferentBusStopWithMatchedOnRecord() {
        TapRecord onRecord = TapRecord.builder()
                .id(1)
                .tapAt(OffsetDateTime.now())
                .tapType(ON)
                .busId("Bus1")
                .companyId("Company1")
                .stopId("Stop1")
                .pan("11111")
                .build();
        Trip trip = calculator.calculateTrip(onRecord);
        assertNull(trip);

        TapRecord offRecord = TapRecord.builder()
                .id(2)
                .tapAt(OffsetDateTime.now().plusMinutes(10))
                .tapType(OFF)
                .busId("Bus1")
                .companyId("Company1")
                .stopId("Stop2")
                .pan("11111")
                .build();

        when(factory.getGenerator(eq(COMPLETED.status))).thenReturn(completedTripGenerator);
        calculator.calculateTrip(offRecord);
        verify(factory).getGenerator(eq(COMPLETED.status));
        verify(completedTripGenerator).generate(eq(onRecord), eq(offRecord));
    }

    @Test
    void shouldInvokeIncompleteTripGeneratorWhenGettingOffTapWithSameBusStopWithMatchedOnRecord() {
        TapRecord onRecord = TapRecord.builder()
                .id(1)
                .tapAt(OffsetDateTime.now())
                .tapType(ON)
                .busId("Bus1")
                .companyId("Company1")
                .stopId("Stop1")
                .pan("11111")
                .build();
        Trip trip = calculator.calculateTrip(onRecord);
        assertNull(trip);

        TapRecord offRecord = TapRecord.builder()
                .id(2)
                .tapAt(OffsetDateTime.now().plusMinutes(15))
                .tapType(ON)
                .busId("Bus2")
                .companyId("Company1")
                .stopId("Stop3")
                .pan("11111")
                .build();

        when(factory.getGenerator(eq(INCOMPLETE.status))).thenReturn(incompleteTripGenerator);
        calculator.calculateTrip(offRecord);
        verify(factory).getGenerator(eq(INCOMPLETE.status));
        verify(incompleteTripGenerator).generate(eq(onRecord), eq(offRecord));
    }

}
