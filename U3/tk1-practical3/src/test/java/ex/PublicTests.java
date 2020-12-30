package ex;

import ex.deserialization.FlightParser;
import ex.deserialization.FlightParserImpl;
import ex.deserialization.objects.Flight;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PublicTests {

    static final String DEPARTURES = "./Fraport/*DEPARTURES*";
    static final String FLIGHTS_PATH = "./Fraport/2018-08-0*";
    static AirportInfo uut;
    static FlightParser fput;

    static Dataset<Row> departureRows;

    @BeforeAll
    static void parseFlights() {
        Logger.getLogger("org").setLevel(Level.ERROR);
        Logger.getLogger("akka").setLevel(Level.ERROR);
        uut = new AirportInfoImpl();
        fput = new FlightParserImpl();
        departureRows = fput.parseRows(DEPARTURES);
    }

    @Nested
    @DisplayName("Parser")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class FlightParserTest {

        List<String> testListOfFlights;

        @BeforeAll
        @Timeout(30)
        void setup() {
            Dataset<Flight> flights = fput.parseFlights(FLIGHTS_PATH);
            testListOfFlights = flights.map(f -> f.toString(), Encoders.STRING()).takeAsList(6540);
        }

        @ParameterizedTest
        @CsvFileSource(resources = "/parser.csv")
        @DisplayName("Test parser output")
        void testParser(String key) {
            assertTrue(testListOfFlights.contains(key));
        }
    }

    @Nested
    @DisplayName("Task 3")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class AircraftCountOnDate {

        List<String> list;

        @BeforeAll
        @Timeout(10)
        void setup() {
            list = new ArrayList<>();
            JavaPairRDD<String, Long> result = uut.aircraftCountOnDate(departureRows, "2018-08-08");
            result.take(60).forEach(t -> list.add(t._1()));
        }

        @Test
        @DisplayName("Compare entry")
        void entryShouldExist() {
            assertTrue(list.contains("A32A"), "Missing entry");
        }

        @Test
        @DisplayName("Number of elements")
        void numberOfElementsShouldBeInRange() {
            assertTrue(list.size() >= 30 && list.size() <= 60, "Wrong number of elements");
        }
    }

    @Nested
    @DisplayName("Task 2")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class GatesToBerlin {

        Dataset<Row> result;

        @BeforeAll
        @Timeout(10)
        void setup() {
            result = uut.gatesWithFlightsToBerlin(departureRows);
        }

        @Test
        @DisplayName("Number of columns")
        void checkNumberOfColumns() {
            assertEquals(2, result.columns().length, "Wrong number of columns.");
        }

        @Test
        @DisplayName("Names and types of columns")
        void checkColumns() {
            Tuple2<String, String>[] cols = result.dtypes();
            assertEquals("gate", cols[0]._1(), "Name of first column is incorrect.");
            assertEquals("StringType", cols[0]._2(), "Datatype of first column is incorrect.");
            assertEquals("count", cols[1]._1(), "Name of second column is incorrect.");
            assertEquals("LongType", cols[1]._2(), "Datatype of second column is incorrect.");
        }
    }

    @Nested
    @DisplayName("Task 1")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class MostCommonDests {

        Dataset<Row> result;

        @BeforeAll
        @Timeout(10)
        void setup() {
            result = uut.mostCommonDestinations(departureRows);
        }

        @Test
        @DisplayName("Number of columns")
        void checkNumberOfColumns() {
            assertEquals(2, result.columns().length, "Wrong number of columns.");
        }

        @Test
        @DisplayName("Names and types of columns")
        void checkColumns() {
            Tuple2<String, String>[] cols = result.dtypes();
            assertEquals("arrivalAirport", cols[0]._1(), "Name of first column is incorrect.");
            assertEquals("StringType", cols[0]._2(), "Datatype of first column is incorrect.");
            assertEquals("count", cols[1]._1(), "Name of second column is incorrect.");
            assertEquals("LongType", cols[1]._2(), "Datatype of second column is incorrect.");
        }
    }
}
