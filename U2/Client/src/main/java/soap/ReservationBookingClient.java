package soap;
import java.io.Console;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import Flight.*;
import UI.*;

public class ReservationBookingClient {
	//soap client
	
	//public HashMap<Integer,ArrayList<String>> flightsForOneWeek;
	public String clintID;
	public ReservationBookingService server;
	public Shell shell;
	
	public  ReservationBookingClient(String id){
		this.clintID=id;
	}
	public boolean book(int day,int index,int row,int clone,String meal) {
		return server.book(day,index,row,clone,meal);
	}
	
	public void runClient() {
		//UI
		Display display = new Display();
		shell = new Shell(display);
		shell.setLayout(new GridLayout(1,false));	
		ClientUI ui = new ClientUI(shell,SWT.NONE);	
		ui.setClient(this);
		ui.setDisplay(display);
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
	       
	    URL url = new URL("http://localhost:8090/bookingservice?wsdl");
	    //1st argument service URI, refer to wsdl document above
	    //2nd argument is service name, refer to wsdl document above
	    QName qname = new QName("http://soap/", "ReservationBookingServiceImplService");
	    Service service = Service.create(url, qname);
	    ReservationBookingService server = service.getPort(ReservationBookingService.class);
	    //System.out.println(server.getHelloWorldAsString("mkyong"));
	
	    ReservationBookingClient client = new ReservationBookingClient(UUID.randomUUID().toString());
	    client.server=server;
	    client.runClient();

	}

}
