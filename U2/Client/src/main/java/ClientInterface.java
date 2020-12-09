import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.widgets.Shell;

import soap.ReservationBookingService;

public interface ClientInterface {
	
	
	public boolean book(int day,int index,int row,int clone,String meal);
	public void runClient();
	public HashMap<Integer,ArrayList<String>> getFlights();
}
