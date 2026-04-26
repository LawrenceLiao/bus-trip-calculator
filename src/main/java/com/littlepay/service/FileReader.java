package com.littlepay.service;

import com.littlepay.helper.DateTimeHelper;
import com.littlepay.model.TapRecord;
import com.littlepay.model.TapType;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    private static final String ID = "ID";
    private static final String TAP_TYPE = "TapType";
    private static final String STOP_ID = "StopId";
    private static final String COMPANY_ID = "CompanyId";
    private static final String BUS_ID = "BusID";
    private static final String PAN = "PAN";
    private static final String DATE_TIME_UTC = "DateTimeUTC";

    private static final DateTimeFormatter DATE_FORMAT =
            new DateTimeFormatterBuilder()
                    .appendPattern(DateTimeHelper.DATE_TIME_PATTERN)
                    .parseDefaulting(ChronoField.OFFSET_SECONDS, 0)
                    .toFormatter();

    private static final CSVFormat CSV_FORMAT = CSVFormat.DEFAULT.builder()
            .setHeader()
            .setSkipHeaderRecord(true)
            .setIgnoreSurroundingSpaces(true)
            .setTrim(true)
            .setIgnoreEmptyLines(true)
            .build();

    public List<TapRecord> read(Path path) throws IOException {
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return read(reader);
        }
    }

    private List<TapRecord> read(Reader reader) throws IOException {
        List<TapRecord> taps = new ArrayList<>();
        try (CSVParser parser = CSV_FORMAT.parse(reader)) {
            for (CSVRecord r : parser) {
                taps.add(toTap(r));
            }
        }
        return taps;
    }

    private TapRecord toTap(CSVRecord r) {
        return new TapRecord(
                Long.parseLong(r.get(ID)),
                OffsetDateTime.parse(r.get(DATE_TIME_UTC), DATE_FORMAT),
                TapType.valueOf(r.get(TAP_TYPE)),
                r.get(STOP_ID),
                r.get(COMPANY_ID),
                r.get(BUS_ID),
                r.get(PAN)
        );
    }
}
