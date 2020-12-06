package Flight;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

public class Seat implements Serializable {
	@XmlElement(required = true)
	public int row;
	
	@XmlElement(required = true)
	public int number;
	
	@XmlElement(required = true)
	public boolean booked;
	
	@XmlElement(required = true)
	public String type;  // First Class, Economy Plus Class, and Economy Class
	
	@XmlElement(required = true)
	public String meal;  // Standard, Vegetarian, or Vegan
	
	public Seat(int i,int j, String type) {
		this.row = i;
		this.number = j;
		this.booked =false;
		this.type = type;  // First Class, Economy Plus Class, and Economy Class
		this.meal ="Standard";  // Standard, Vegetarian, or Vegan
	}
}
