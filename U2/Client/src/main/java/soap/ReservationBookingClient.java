package soap;
import java.io.Console;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import Flight.*;
import UI.*;

public class ReservationBookingClient {
	//soap client
	
	public HashMap<Integer, List<Flight>> flightsForOneWeek;
	public String clintID;
	
	public  ReservationBookingClient(String id){
		this.clintID=id;
	}
	
	public void runClient(ReservationBookingServiceInterface server) {
		this.flightsForOneWeek = server.getFlights();
		//System.out.println("flights size£º"+this.flightsForOneWeek.toString());
		//UI
		Display display = new Display();
		Shell shell = new Shell(display);
		//shell.setLayout(new GridLayout(1,false));
		
		ClientUI ui = new ClientUI(shell,SWT.NONE);	
		ui.setClient(this);
		shell.pack();
		shell.open();
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch()) {
				display.sleep();
			}
		 }
		 
		 display.dispose();
	}
	
	public static void main(String[] args) throws Exception {
	       
	    URL url = new URL("http://localhost:8090/bookingservice");
	    //1st argument service URI, refer to wsdl document above
	    //2nd argument is service name, refer to wsdl document above
	    QName qname = new QName("http://soap/", "ReservationBookingServiceService");
	    Service service = Service.create(url, qname);
	    ReservationBookingServiceInterface server = service.getPort(ReservationBookingServiceInterface.class);
	    //System.out.println(server.getHelloWorldAsString("mkyong"));
	
	    ReservationBookingClient client = new ReservationBookingClient(UUID.randomUUID().toString());
	    //client.runClient(server);
	    System.out.println("flights"+server.getFlights().toString());

	}

}
