package helper;

import com.littlepay.helper.DateTimeHelper;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateTimeHelperTest {

    @Test
    void shouldReturnEmptyStringWhenGivenDateTimeIsNull() {
        String result = DateTimeHelper.formatDate(null);
        assertEquals("", result);
    }

    @Test
    void shouldReturnDateTimeStringWithRequiredFormat() {
        OffsetDateTime dateTime = OffsetDateTime.of(2026, 4,30,1,1, 1, 1, ZoneOffset.UTC);
        String result = DateTimeHelper.formatDate(dateTime);
        assertEquals("30-04-2026 01:01:01", result);
    }
}
