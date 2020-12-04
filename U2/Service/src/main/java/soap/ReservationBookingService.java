package soap;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jws.WebService;

import Flight.Flight;
 
//Service Endpoint Interface
@WebService(endpointInterface = "soap.ReservationBookingServiceInterface")
public class ReservationBookingService  implements  ReservationBookingServiceInterface{
	
	public HashMap<Integer, List<Flight>> flightsForOneWeek = new HashMap<Integer, List<Flight>>();
	
	public ReservationBookingService() throws RemoteException {
		this.initFlights();
	
	}
	
	public void initFlights() throws RemoteException  {
		// TODO Auto-generated method stub
		
		for(int day=0;day<=6;day++) {
			 List<Flight> flightsForOneDay = new ArrayList<Flight>();
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
	public HashMap<Integer, List<Flight>> getFlights() {
		// TODO Auto-generated method stub
		return  this.flightsForOneWeek;
	}

	@Override
	public boolean getBookingResult() {
		// TODO Auto-generated method stub
		return true;
	}

}