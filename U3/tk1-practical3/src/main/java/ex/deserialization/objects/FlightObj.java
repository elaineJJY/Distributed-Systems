package ex.deserialization.objects;

import java.io.Serializable;

/**
 * The object to map the outermost JSON object to. Contains the actual flight object, which is a member of the
 * actual JSON object.
 */
public class FlightObj implements Serializable {

    private Flight flight;

    public FlightObj(Flight flight) {
        this.flight = flight;
    }

    public Flight getFlight() {
        return flight;
    }
}
