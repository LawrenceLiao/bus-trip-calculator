package com.littlepay.helper;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {
    public static final String DATE_TIME_PATTERN = "dd-MM-yyyy HH:mm:ss";
    public static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
    public static String formatDate(OffsetDateTime dateTime) {
        if (dateTime == null) return "";
        return dateTime.withOffsetSameInstant(ZoneOffset.UTC).format(FORMATTER);
    }
}
