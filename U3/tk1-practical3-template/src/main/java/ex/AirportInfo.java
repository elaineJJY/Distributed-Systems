package ex;

import ex.deserialization.objects.Flight;

import java.text.ParseException;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface AirportInfo {

    /**
     * Task 1
     * Return a dataframe, in which every row contains the destination airport and its count over all available data
     * of departing flights, sorted by count in descending order.
     * The column names are (in this order):
     * arrivalAirport | count
     * Remove entries that do not contain an arrivalAirport member (null or empty).
     * Use the given Dataframe.
     *
     * @param departingFlights Dataframe containing the rows of departing flights
     * @return a dataframe containing statistics of the most common destinations
     */
    Dataset<Row> mostCommonDestinations(Dataset<Row> departingFlights);

    /**
     * Task 2
     * Return a dataframe, in which every row contains a gate and the amount at which that gate was used for flights to
     * Berlin ("count"), sorted by count in descending order. Do not include gates with 0 flights to Berlin.
     * The column names are (in this order):
     * gate | count
     * Remove entries that do not contain a gate member (null or empty).
     * Use the given Dataframe.
     *
     * @param departureFlights Dataframe containing the rows of departing flights
     * @return dataframe with statistics about flights to Berlin per gate
     */
    Dataset<Row> gatesWithFlightsToBerlin(Dataset<Row> departureFlights);

    /**
     * Task 3
     * Return a JavaPairRDD with String keys and Long values, containing count of flights per aircraft on the given
     * originDate. The String keys are the modelNames of each aircraft and their Long value is the amount of flights for
     * that modelName at the given originDate. Do not include aircrafts with 0 flights.
     * Remove entries that do not contain a modelName member (null or empty).
     * The date string is of the form 'YYYY-MM-DD'.
     * Use the given dataframe.
     *
     * @param flights    Dataframe containing the rows of flights
     * @param originDate the date to find the most used aircraft for
     * @return tuple containing the modelName of the aircraft and its total number
     */
    JavaPairRDD<String, Long> aircraftCountOnDate(Dataset<Row> flights, String originDate);

    /**
     * Task 4
     * Returns the date string of the day at which Ryanair had a strike in the given Dataframe.
     * The returned string is of the form 'YYYY-MM-DD'.
     * Hint: There were strikes at two days in the given period of time. Both are accepted as valid results.
     *
     * @param flights Dataframe containing the rows of flights
     * @return day of strike
     */
    String ryanairStrike(Dataset<Row> flights);

    /**
     * Task 5
     * Returns a dataset of Flight objects. The dataset only contains flights of the given airline with at least one
     * of the given status codes. Uses the given Dataset of Flights.
     *
     * @param flights            Dataset containing the Flight objects
     * @param airlineDisplayCode the display code of the airline
     * @param status1            the status code of the flight
     * @param status             more status codes
     * @return dataset of Flight objects matching the required fields
     */
    Dataset<Flight> flightsOfAirlineWithStatus(Dataset<Flight> flights, String airlineDisplayCode, String status1, String... status);

    /**
     * Task 6
     * Returns the average number of flights per day between the given timestamps (both included).
     * The timestamps are of the form 'hh:mm:ss'. Uses the given Dataset of Flights.
     * Hint: You only need to consider "scheduledTime" for this. Do not include flights with
     * empty "scheduledTime" field. You can assume that lowerLimit is always before or equal
     * to upperLimit.
     *
     * @param flights Dataset containing the arriving Flight objects
     * @param lowerLimit     start timestamp (included)
     * @param upperLimit     end timestamp (included)
     * @return average number of flights between the given timestamps (both included)
     * @throws ParseException 
     */
    double avgNumberOfFlightsInWindow(Dataset<Flight> flights, String lowerLimit, String upperLimit);

}
