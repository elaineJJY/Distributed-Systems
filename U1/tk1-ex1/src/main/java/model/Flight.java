package model;


import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.UUID;

public class Flight implements Serializable {
	public String airline;
	public String flightNumber;
	
	public String departure;  
	public String departureTime;
	public String departureTerminal;
	
	public String id;
	
	public String arrive;
	public String arrivalTime;
	public String arrivalTerminal;
	
	public String airlineName;
	
	public Flight() throws RemoteException{
		this.airline="";
		this.flightNumber="";
		
		this.departure="FRA";
		this.departureTime="2020-00-00";
		this.departureTerminal="T1";
		
		this.arrive="TK";
		this.arrivalTime="2020-00-00";
		this.arrivalTerminal="T2";
		this.airlineName="name";
		id = UUID.randomUUID().toString(); 
		
	}
	

	public void init(String airline,String flightNumber)throws RemoteException {
		this.airline=airline;
		this.flightNumber=flightNumber;
		
	}
}
