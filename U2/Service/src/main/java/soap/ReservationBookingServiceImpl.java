package soap;


import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


import javax.jws.WebService;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.util.TypeUtils;
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
				 flightsForOneDay.add(new Flight(String.valueOf(day),flightType));
			 }
			 flightsForOneWeek.put(day,flightsForOneDay);
		}
		//System.out.println("flights size£º"+flightsForOneWeek.size());
	}
	
	
	@Override
	public HashMap<Integer, ArrayList<String>> getFlights() {
		// TODO Auto-generated method stub
		HashMap<Integer, ArrayList<String>> flightsInString = new HashMap<Integer, ArrayList<String>>();
		
		for(int day = 0; day < 7 ; day++) {
			ArrayList<String> flightsForOneDayInString = new ArrayList<String>();
			for(int j = 0; j<flightsForOneWeek.get(day).size();j++) {
				Flight flight = flightsForOneWeek.get(day).get(j);
				String resultJson = JSON.toJSONString(flight);
				//System.out.println(resultJson);
				flightsForOneDayInString.add(resultJson);
			}
			flightsInString.put(day, flightsForOneDayInString);
		}
		return  flightsInString;
	}


	@Override
	public boolean book(int day,int index,int row,int clone,String meal){
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
	public String test() {
		// TODO Auto-generated method stub
		//System.out.println("services"+l.toString());
		String objectMapJson = JSON.toJSONString(this.flightsForOneWeek);
		objectMapJson = "asdfsdfsdf";
		return objectMapJson;
	}

}