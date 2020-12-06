package Flight;

import java.io.Serializable;
import java.rmi.RemoteException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
//@XmlType(name = "Seat")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "seat", propOrder = {"row", "clone", "booked", "type", "meal","reservationID"})
public class Seat implements Serializable{
	@XmlElement(name="row",required = true)
	public int row;
	
	@XmlElement(name="clone",required = true)
	public int clone;
	
	@XmlElement(name="booked",required = true)
	public boolean booked;
	
	@XmlElement(name="type",required = true)
	public String type;  // First Class, Economy Plus Class, and Economy Class
	
	@XmlElement(name="meal",required = true)
	public String meal;  // Standard, Vegetarian, or Vegan
	
	@XmlElement(name="reservationID",required = true)
	public String reservationID;
	
	public Seat()throws RemoteException{
		
	}
	
	public Seat(int i,int j, String type)throws RemoteException {
		this.row = i;
		this.clone = j;
		this.booked =false;
		this.type = type;  // First Class, Economy Plus Class, and Economy Class
		this.meal ="Standard";  // Standard, Vegetarian, or Vegan
	}
}
