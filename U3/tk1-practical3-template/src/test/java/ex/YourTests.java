package ex;

import ex.deserialization.FlightParser;
import ex.deserialization.FlightParserImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;

public class YourTests {

    static AirportInfo uut;
    static FlightParser fput;

    @BeforeAll
    static void init() {
        Logger.getLogger("org").setLevel(Level.ERROR);
        Logger.getLogger("akka").setLevel(Level.ERROR);
        uut = new AirportInfoImpl();
        fput = new FlightParserImpl();
    }
    
    
}
