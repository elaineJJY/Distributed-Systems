package rest;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.websocket.server.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import com.alibaba.fastjson.JSON;

import Flight.Flight;
import Flight.Seat;
import javassist.bytecode.annotation.BooleanMemberValue;

@Path("/rest")
public class ReservationBookingController {
	 	
	
		@XmlElementWrapper(name = "flightsForOneWeek")
	    @XmlElement(name = "flight")
		public static HashMap<Integer, ArrayList<Flight>> flightsForOneWeek = new HashMap<Integer, ArrayList<Flight>>();

		public ReservationBookingController() throws RemoteException {
			if(this.flightsForOneWeek.size()==0) {
				this.initFlights();
			}
			
		}
			
		public void initFlights() throws RemoteException  {
			// TODO Auto-generated method stub		
			for(int day=0;day<=6;day++) {
				ArrayList<Flight> flightsForOneDay = new ArrayList<Flight>();
				 for(int i=0;i<=8;i++) {
					 String flightType = "Boeing 737-900";
					 if(i>2) {
						 flightType = "Airbus 319";
					 }
					 if(i>5) {
						 flightType = "Embraer E170";
					 }
					 flightsForOneDay.add(new Flight(String.valueOf(day),flightType));
				 }
				 flightsForOneWeek.put(day,flightsForOneDay);
			}
		}
		
		
		
		@GET
		@Path("/flights")
		@Produces(MediaType.APPLICATION_JSON)
		public FlightsList getFlights() {
			// TODO Auto-generated method stub
			//HashMap<Integer, ArrayList<String>> flightsInString = new HashMap<Integer, ArrayList<String>>();
			HashMap<Integer, String> flightsInString = new HashMap<Integer, String>();
			
			for(int day = 0; day < 7 ; day++) {
				ArrayList<String> flightsForOneDayInString = new ArrayList<String>();
				for(int j = 0; j<flightsForOneWeek.get(day).size();j++) {
					Flight flight = flightsForOneWeek.get(day).get(j);
					String resultJson = JSON.toJSONString(flight);
					//System.out.println(resultJson);
					flightsForOneDayInString.add(resultJson);
				}
				
				flightsInString.put(day, JSON.toJSONString(flightsForOneDayInString));
			}
			
			//return  flightsInString;
			FlightsList list = new FlightsList();
			//list.setFlights(flightsInString);
			list.flightsForOneWeek=flightsInString;
			//String list = JSON.toJSONString(flightsInString);
			return list;
		}


			
			@POST
			@Path("/book")
			@Produces(MediaType.TEXT_PLAIN)
			public Response book(@FormParam("day")int day,
					@FormParam("index")int index,
					@FormParam("row")int row,
					@FormParam("clone")int clone,
					@FormParam("meal")String meal){
			// TODO Auto-generated method stub
			Flight f=this.flightsForOneWeek.get(day).get(index);
			Seat seat = f.seats[row][clone];
			if(!seat.booked) {
				seat.booked=true;
				seat.meal=meal;
				seat.reservationID = UUID.randomUUID().toString();
				return Response
					      .status(Response.Status.OK)
					      .entity(true)
					      .build();
			}
			return Response
				      .status(Response.Status.OK)
				      .entity(false)
				      .build();
			
		}
		

	
}
