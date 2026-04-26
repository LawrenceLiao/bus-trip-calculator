package com.littlepay;

import com.littlepay.calculator.TripCalculator;
import com.littlepay.model.TapRecord;
import com.littlepay.model.Trip;
import com.littlepay.csv.FileReader;
import com.littlepay.csv.FileWriter;
import com.littlepay.service.TripGeneratorFactory;
import com.littlepay.service.TripProcessService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class BusTripCalculatorApplication {
    public static void main(String[] args) {
        if (args.length < 2) {
            log.error("Please specify input and output file paths");
            System.exit(1);
        }
        try {
            process(args[0], args[1]);
        } catch (Exception e) {
            log.error("Error occurred while processing the tap file", e);
        }
    }

    private static void process(String inputPathStr, String outputPathStr) throws IOException {
        Path inputPath = Paths.get(inputPathStr);

        FileReader reader = new FileReader();

        List<TapRecord> list = reader.read(inputPath);
        TripProcessService service = new TripProcessService(new TripCalculator(new TripGeneratorFactory()));
        List<Trip> trips = service.processTrips(list);
        FileWriter writer = new FileWriter();
        writer.write(trips, Paths.get(outputPathStr));
    }
}