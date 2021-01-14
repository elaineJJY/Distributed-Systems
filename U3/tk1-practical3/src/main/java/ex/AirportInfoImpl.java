package ex;

import ex.deserialization.objects.Flight;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.catalyst.plans.logical.Distinct;

import scala.Tuple2;
import scala.reflect.runtime.SynchronizedSymbols.SynchronizedTermSymbol;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.explode;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AirportInfoImpl implements AirportInfo {

    /**
     * Usage of some example operations.
     *
     * @param flights dataframe of flights
     */
    public void sparkExample(Dataset<Row> flights) {
        System.out.println("Example printSchema");
        flights.printSchema();

        System.out.println("Example select and filter");
        // operations on a dataframe or rdd do not modify it, but return a new one
        Dataset<Row> selectAirlineDisplayCode = flights.select("flight.operatingAirline.iataCode", "flight.aircraftType.icaoCode").filter(r -> !r.anyNull());
        selectAirlineDisplayCode.show(false);

        System.out.println("Example groupBy and count");
        Dataset<Row> countOfAirlines = selectAirlineDisplayCode.groupBy("iataCode").count();
        countOfAirlines.show(false);

        System.out.println("Example where");
        Dataset<Row> selectOnlyLufthansa = selectAirlineDisplayCode.where("iataCode = 'LH'");
        selectOnlyLufthansa.show();

        System.out.println("Example map to String");
        Dataset<String> onlyAircraftIcaoCodeAsString = selectOnlyLufthansa.map(r -> r.getString(1), Encoders.STRING());
        onlyAircraftIcaoCodeAsString.show();

        System.out.println("Example mapToPair and reduceByKey");
        JavaRDD<Row> rdd = selectOnlyLufthansa.toJavaRDD();
        JavaPairRDD<String, Long> paired = rdd.mapToPair(r -> Tuple2.apply(r.getString(1), 1L));
        JavaPairRDD<String, Long> reducedByKey = paired.reduceByKey((a, b) -> a + b);
        reducedByKey.take(20).forEach(t -> System.out.println(t._1() + " " + t._2()));
    }

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
    @Override
    public Dataset<Row> mostCommonDestinations(Dataset<Row> departingFlights) {
        // TODO: Implement
    	Dataset<Row> arrivalAirports = departingFlights.select("flight.arrivalAirport").filter(r -> !r.anyNull()).groupBy("arrivalAirport").count();
    	arrivalAirports = arrivalAirports.sort(col("count").desc());
    	//arrivalAirports.show();
        return arrivalAirports;
    }

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
    @Override
    public Dataset<Row> gatesWithFlightsToBerlin(Dataset<Row> departureFlights) {
        // TODO: Implement

    	Dataset<Row> flightsToBerlin= departureFlights.select("flight.arrivalAirport",
    														"flight.departure.gates.gate").filter(r -> !r.anyNull());
    	//filter the Flight to Berlin
    	flightsToBerlin= flightsToBerlin.select(col("arrivalAirport"),explode(col("gate")).as("gate"));
    	flightsToBerlin=flightsToBerlin.filter(col("arrivalAirport").contains("BER").or(col("arrivalAirport").contains("TXL")));
    	
    	flightsToBerlin=flightsToBerlin.groupBy("gate").count().sort(col("count").desc());
        //flightsToBerlin.show();
    	return flightsToBerlin;
    }

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
    @Override
    public JavaPairRDD<String, Long> aircraftCountOnDate(Dataset<Row> flights, String originDate) {
        // TODO: Implement
        JavaRDD<Row> rdd = flights.filter(col("flight.originDate").equalTo(originDate)).select("flight.aircraftType.modelName").filter(r -> !r.anyNull()).toJavaRDD();
        JavaPairRDD<String, Long> paired = rdd.mapToPair(r -> Tuple2.apply(r.getString(0), 1L));
        JavaPairRDD<String, Long> reducedByKey = paired.reduceByKey((a, b) -> a + b);
        reducedByKey.filter(t -> t._2()>0 );    // exclude aircrafts with 0 flights on the given date 
        //reducedByKey.take(20).forEach(t -> System.out.println(t._1() + " " + t._2()));
        return reducedByKey;
    }

    /**
     * Task 4
     * Returns the date string of the day at which Ryanair had a strike in the given Dataframe.
     * The returned string is of the form 'YYYY-MM-DD'.
     * Hint: There were strikes at two days in the given period of time. Both are accepted as valid results.
     *
     * @param flights Dataframe containing the rows of flights
     * @return day of strike
     */
    @Override
    public String ryanairStrike(Dataset<Row> flights) {
        // TODO: Implement
    	Dataset<Row> ryanairStrikeflights = flights.select("flight.operatingAirline.iataCode","flight.originDate").filter(col("iataCode").equalTo("FR"));
    	String date = ryanairStrikeflights.select("originDate").first().getString(0);
    	//System.out.print(date);
        return null;
    }

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
    @Override
    public Dataset<Flight> flightsOfAirlineWithStatus(Dataset<Flight> flights, String airlineDisplayCode, String status1, String... status) {
        // TODO: Implement
    	Dataset<Flight> airLineFliter = flights.filter(f->{
    		boolean airlineEq=f.getAirlineDisplayCode().equals(airlineDisplayCode);
    		boolean containStatus=false;
    		String s=f.getFlightStatus();
    		if(status1.equals(s)) {
    			containStatus =true;
    		}
    		else {
    			if((status.length>0)) {
    				for (int i = 0; i < status.length; i++) {  
    					if(status[i].equals(s)) {
    		    			containStatus =true;
    		    			break;
    		    		}
    		        }  
    			}
    		}
    		return airlineEq && containStatus;
    	});
    	
        return airLineFliter;
    }

    /**
     * Task 6
     * Returns the average number of flights per day between the given timestamps (both included).
     * The timestamps are of the form 'hh:mm:ss'. Uses the given Dataset of Flights.
     * Hint: You only need to consider "scheduledTime" and "originDate" for this. Do not include flights with
     * empty "scheduledTime" or "originDate" fields. You can assume that lowerLimit is always before or equal
     * to upperLimit.
     *
     * @param flights Dataset containing the arriving Flight objects
     * @param lowerLimit     start timestamp (included)
     * @param upperLimit     end timestamp (included)
     * @return average number of flights between the given timestamps (both included)
     */
    @Override
    public double avgNumberOfFlightsInWindow(Dataset<Flight> flights, String lowerLimit, String upperLimit){
        // TODO: Implement
    	Dataset<Flight> flightsInRange = flights.filter(f->{
    		String originDate = f.getOriginDate();
    		String scheduledTime = f.getScheduledTime();
    		
    		// Do not include flights with empty "scheduledTime" or "originDate" fields.
    		if(originDate.length()==0 || scheduledTime.length()==0) {
    			return false;
    		}
    		
    		// filter the flights in time range
    		if(isBefore(lowerLimit, scheduledTime) && isBefore(scheduledTime, upperLimit)){
    			return true;
    		}
    		return false;
    		
    	});
    	//flightsInRange.show();
    	long flightsNumber = flightsInRange.count();
    	long difference = flightsInRange.select("originDate").distinct().count();
        return (double)flightsNumber/(double)difference;
    }

    /**
     * Returns true if the first timestamp is before or equal to the second timestamp. Both timestamps must match
     * the format 'hh:mm:ss'.
     * You can use this for Task 6. Alternatively use LocalTime or similar API (or your own custom method).
     *
     * @param before the first timestamp
     * @param after  the second timestamp
     * @return true if the first timestamp is before or equal to the second timestamp
     */
    private static boolean isBefore(String before, String after) {
        for (int i = 0; i < before.length(); i++) {
            char bef = before.charAt(i);
            char aft = after.charAt(i);
            if (bef == ':') continue;
            if (bef < aft) return true;
            if (bef > aft) return false;
        }

        return true;
    }
    
    /**
     * Returns the difference of the day between loewerLimit and upperLimit 
     * @param lowerLimit the first timestamp
     * @param upperLimit  the second timestamp
     * @return the difference of the day between loewerLimit and upperLimit
     */
    private static int getTimeDifference(String lowerLimit, String upperLimit){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	try {
    		Date fromDate=(Date) format.parse(lowerLimit);
    		Date toDate=(Date) format.parse(upperLimit);
    		long from = fromDate.getTime();  
    	    long to = toDate.getTime();  
    	    int days = (int) ((to - from) / (1000 * 60 * 60 * 24));
    	    return days;
    	} catch (ParseException e) {
           e.printStackTrace();
    	}
    	return -1;
    }
}
