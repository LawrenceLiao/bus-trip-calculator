# Bus Trip Calculator
## Tech Stack:
* Java 21
* Lombok
* JUnit
* Mockito

## Topics
1. [How to run this application](#How-to-run-this-application)
2. [Design and implementation](#Design-and-implementation)
3. [Assumptions and TradeOffs](#Assumptions-and-tradeoffs)

## How to run this application

* Navigate the root folder /bus-trip-calculator under the command line
* Run the command to build the whole project:
  `./gradlew clean shadowJar`
* Run the command to start the application:
  `java -jar ./build/libs/bus-trip-calculator-1.0-SNAPSHOT-all.jar taps.csv trips.csv`
* Or you can run in IDE directly for BusTripCalculatorApplication with arguments `taps.csv` and  `trips.csv` 

## Design and implementation
* Set up domain models for Trip, TapRecord, TripFare
* Define generators for handling different trip types and leverage Factory Pattern to determine which generator is applicable
* Use OffsetDateTime for the date time type since we need to store time zone information and can convert them easily in any time zone
* Implement the trip fare calculation in calculator
* Separate io operation and business logic
* The calculator part is implemented with Event-Driven like pattern, when we get a tap event then process it

## Assumptions and TradeOffs
* The input taps file is sorted by tap time
* The input taps file is not too big and then can be loaded into memory
* The order of output is not important as long as the results are correct
* If the tap on is missing, the trip will be skipped
* If the tap off is missing, the trip will be calculated by the last tap on as an incomplete trip
* Assume Incomplete Trips show empty finished date time and 0 for duration seconds
* Configured all fares in a map for easy to use
* Didn't optimise max fare calculation which can be done by maintaining a max value for every stop
* Didn't handle invalid data from CSV files, assume the input file is always well-formatted
* Didn't have unit tests for csv file reader and writer
* Didn't check other fields(busId, companyId etc.), only look at PAN, assume the fields are all matched
* Only handled some exceptions
* The implementation is based on Event-Driven pattern, so those tap ON records without following relevant record will not be processed,
I think we can implement another scheduled job to go through all unmatched tap on records to build incomplete trip and calculate charge amount
