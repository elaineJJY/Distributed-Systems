package ex.deserialization;

import ex.deserialization.objects.Flight;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface FlightParser {

    /**
     * Returns a Dataframe, containing all objects from the JSON files in the given path.
     *
     * @param path the path containing (only) the JSON files
     * @return Dataframe containing the input data
     */
    Dataset<Row> parseRows(String path);

    /**
     * Deserializes the JSON lines to Flight objects. Each String in the given Dataset represents a JSON object.
     * Use Gson for this.
     *
     * @param path the path to parse the jsons from
     * @return dataset of Flight objects parsed from the JSON lines
     */
    Dataset<Flight> parseFlights(String path);
}
