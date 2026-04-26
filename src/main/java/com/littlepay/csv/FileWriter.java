package com.littlepay.csv;

import com.littlepay.model.Trip;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.littlepay.helper.DateTimeHelper.formatDate;
import static com.littlepay.helper.MoneyHelper.formatMoney;

public class FileWriter {
    private static final String STARTED = "Started";
    private static final String FINISHED = "Finished";
    private static final String DURATION_SECS = "DurationSecs";
    private static final String FROM_STOP_ID = "FromStopId";
    private static final String TO_STOP_ID = "ToStopId";
    private static final String CHARGE_AMOUNT = "ChargeAmount";
    private static final String COMPANY_ID = "CompanyId";
    private static final String BUS_ID = "BusID";
    private static final String PAN = "PAN";
    private static final String STATUS = "Status";

    private static final String[] HEADERS = {
            STARTED, FINISHED, DURATION_SECS, FROM_STOP_ID, TO_STOP_ID, CHARGE_AMOUNT, COMPANY_ID, BUS_ID, PAN, STATUS
    };

    private static final String DELIMITER = ", ";
    private static final String RECORD_SEPARATOR = "\n";

    private static final CSVFormat OUTPUT_FORMAT = CSVFormat.DEFAULT.builder()
            .setHeader(HEADERS)
            .setDelimiter(DELIMITER)
            .setRecordSeparator(RECORD_SEPARATOR)
            .build();

    public void write(List<Trip> trips, Path path) throws IOException {
        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            write(trips, writer);
        }
    }

    private void write(List<Trip> trips, Writer writer) throws IOException {
        try (CSVPrinter printer = new CSVPrinter(writer, OUTPUT_FORMAT)) {
            for (Trip trip : trips) {
                printer.printRecord(
                        formatDate(trip.started()),
                        formatDate(trip.finished()),
                        trip.durationSecs(),
                        trip.fromStopId(),
                        trip.toStopId(),
                        formatMoney(trip.chargeAmount()),
                        trip.companyId(),
                        trip.busId(),
                        trip.pan(),
                        trip.status()
                );
            }
            printer.flush();
        }
    }
}
