package Flight;

public class Seat {
	public int row;
	public int number;
	public boolean booked;
	public String type;  // First Class, Economy Plus Class, and Economy Class
	public String meal;  // Standard, Vegetarian, or Vegan
	
	public Seat(int i,int j, String type) {
		this.row = i;
		this.number = j;
		this.booked =false;
		this.type = type;  // First Class, Economy Plus Class, and Economy Class
		this.meal ="Standard";  // Standard, Vegetarian, or Vegan
	}
}
