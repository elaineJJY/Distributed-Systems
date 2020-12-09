package rest;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@Path("/flights")
@XmlRootElement(name = "FlightsList")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"flightsForOneWeek"})
public class FlightsList {
	public HashMap<Integer, String> flightsForOneWeek;
	
	public void setFlights(HashMap<Integer,String> flightsForOneWeek) {
        this.flightsForOneWeek = flightsForOneWeek;
    } 
	
	@GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public HashMap<Integer, String> getFlights( ) {
        return this.flightsForOneWeek;
    } 
}
