package Flight;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;



@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "flight",propOrder = {"airline", "flightNumber", "flightType", "departureWeekDay","destination", "seats", "departure","arrive"})
public class Flight implements Serializable {
	
	
    @XmlElement(name="airline", required=true)
	public String airline;
  
    @XmlElement(name="flightNumber", required=true)
	public String flightNumber;
    
    @XmlElement(name="flightType", required=true)
	public String flightType; //"Boeing 737-900" ,"Airbus 319","Embraer E170"
    
    @XmlElement(name="departureWeekDay", required=true)
	public int departureWeekDay;  //0..6
	
    @XmlElement(name="destination", required=true)
    public int destination;
    
    //@XmlElement(name="seats", required=true)
    @XmlElementWrapper(name = "seats",required=true)
    @XmlElement(name = "seat",required=true)
	public Seat[][] seats;  //the first index is row, the second index is number
	
    @XmlElement(name="departure", required=true)
	public String departure;  

    @XmlElement(name="arrive", required=true)
	public String arrive;

    public Flight() {
    	
    }
    
	public Flight(int day,String type) throws RemoteException{
		this.airline="FRA";
		this.flightNumber="00000000";
		this.departureWeekDay=day;
		this.destination=1800;
		this.departure = "city1";
		this.arrive="city2";
		this.flightType=type;
		//this.initSeat();  
	}
	
	public Flight(int day,String airline,String flightNumber,String type) throws RemoteException{
		this.airline=airline;
		this.flightNumber=flightNumber;
		this.departureWeekDay=day;
		this.destination=1800;
		this.departure = "city1";
		this.arrive="city2";
		this.flightType=type;
		//this.initSeat(); 
	}
	
	public void initSeat() throws RemoteException{
		int row;
		int clone;
		if(flightType == "Boeing 737-900") {
			row = 40;
			clone = 6;
		}
		else if(flightType=="Airbus 319") {
			row = 36;
			clone = 6;
		}
		else{
			row = 25;
			clone = 4;
		}
		this.seats=new Seat[row][clone];
		for(int i=0; i<seats.length;i++) {
			for(int j=0;j<seats[i].length;j++) {
				String type = "Economy Class";
				if(i<5) {
					type = "Economy Plus Class";
				}
				if(i<2) {
					type = "First Class";
				}
				seats[i][j]=new Seat(i,j,type);
			}
		}
	}

//	public void init(String airline,String flightNumber)throws RemoteException {
//
//		
//	}
}
