package service;


import com.littlepay.generator.CancelledTripGenerator;
import com.littlepay.generator.CompletedTripGenerator;
import com.littlepay.generator.IncompleteTripGenerator;
import com.littlepay.generator.TripGenerator;
import com.littlepay.service.TripGeneratorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.littlepay.model.TripStatus.CANCELLED;
import static com.littlepay.model.TripStatus.COMPLETED;
import static com.littlepay.model.TripStatus.INCOMPLETE;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class TripGeneratorFactoryTest {
    private TripGeneratorFactory factory;

    @BeforeEach
    void setUp() {
        factory = new TripGeneratorFactory();
    }

    @Test
    void shouldReturnIncompleteTripGeneratorWhenGivenIncompleteStatus() {
        TripGenerator generator = factory.getGenerator(INCOMPLETE.status);

        assertInstanceOf(IncompleteTripGenerator.class, generator);
    }

    @Test
    void shouldReturnCompletedTripGeneratorWhenGivenCompletedStatus() {
        TripGenerator generator = factory.getGenerator(COMPLETED.status);

        assertInstanceOf(CompletedTripGenerator.class, generator);
    }

    @Test
    void shouldReturnCancelledTripGeneratorWhenGivenCancelledStatus() {
        TripGenerator generator = factory.getGenerator(CANCELLED.status);

        assertInstanceOf(CancelledTripGenerator.class, generator);
    }

    @Test
    void shouldThrowExceptionWhenGivenInvalidStatus() {
        Assertions.assertThrows(RuntimeException.class, () -> factory.getGenerator("invalid"));
    }

}
