package soap;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import Flight.Flight;
import Flight.Seat;
 

@WebService(endpointInterface = "soap.ReservationBookingService")
public class ReservationBookingServiceImpl  implements  ReservationBookingService{
	
	
    @XmlElementWrapper(name = "flightsForOneWeek")
    @XmlElement(name = "flight")
	public HashMap<Integer, ArrayList<Flight>> flightsForOneWeek = new HashMap<Integer, ArrayList<Flight>>();
	
	public ReservationBookingServiceImpl() throws RemoteException {
		this.initFlights();
	
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
				 flightsForOneDay.add(new Flight(day,flightType));
			 }
			 flightsForOneWeek.put(day,flightsForOneDay);
		}
		System.out.println("flights size£º"+flightsForOneWeek.size());
	}
	
	
	@Override
	public HashMap<Integer, ArrayList<Flight>> getFlights() {
		// TODO Auto-generated method stub
		//return  new HashMap<Integer, List<Flight>>();
		return  this.flightsForOneWeek;
	}



	//@Override
	public boolean book(@WebParam(name = "day", partName = "day")int day,
			@WebParam(name = "index", partName = "index")int index,
			@WebParam(name = "row", partName = "row")int row,
			@WebParam(name = "clone", partName = "clone")int clone,
			@WebParam(name = "meal", partName = "meal")String meal) {
		// TODO Auto-generated method stub
		Flight f=this.flightsForOneWeek.get(day).get(index);
		Seat seat = f.seats[row][clone];
		if(seat.booked) {
			return false;
		}
		
		seat.booked=true;
		seat.meal=meal;
		seat.reservationID = UUID.randomUUID().toString();
		return true;
	}
	
	@Override
	public Flight test() {
		// TODO Auto-generated method stub
		//System.out.println("services"+l.toString());
		return this.flightsForOneWeek.get(0).get(0);
	}

}