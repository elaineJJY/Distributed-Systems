import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;

import org.eclipse.swt.widgets.Shell;


import interfaces.IFlightClient;
import interfaces.IFlightServer;
import model.Flight;

public class FlightClient extends UnicastRemoteObject implements IFlightClient {

	private static Logger logger = Logger.getLogger(FlightServer.class.getName());
	public String clientName;
	public IFlightServer stub; 
	private List<Flight> flights = new ArrayList(); 
	private Map<String, Flight> flightsmap= new HashMap<String, Flight>();
	// ui
	private FlightUI ui;

	// global state
	public FlightClient(String clientName) throws RemoteException {
		this.clientName = clientName;
		
	}
	
	public void setConnection(IFlightServer stub) {
		this.stub = stub;
	}
	public void logout() throws RemoteException {
		stub.logout(clientName);
	}
	public void updateMap() {
		flightsmap= new HashMap<String, Flight>();
		for(int i = 0; i<flights.size();i++) {
			Flight f = flights.get(i);
			flightsmap.put(f.id,f);
		}
	}
	
	public void updatelist() {
		flights =  new ArrayList<Flight>(); 
		for (String id : flightsmap.keySet()) {
            flights.add(flightsmap.get(id));
        }
	}
	
	
	public void removeFlight(Flight f) {
		flightsmap.remove(f.id);
		updatelist();
	}

	@Override
	public void receiveListOfFlights(List<Flight> flights)throws RemoteException{
		this.flights = flights;
		updateMap();
		updatelist();
		logger.log(Level.INFO, "List of flights received: " + flights.size());
	}

	@Override
	public void receiveUpdatedFlight(Flight flight, boolean deleted)throws RemoteException {
		if(deleted) {
			removeFlight(flight );
		}
		else {
			flightsmap.put(flight.id,flight);
			updatelist();
		}
		
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				ui.setFlights(flights);	
			}
		});
		
		logger.log(Level.INFO, "Flight updated: " + flight.toString());
	}
	
	
	public void updateFlight(Flight flight) throws RemoteException {
		stub.updateFlight(this.clientName, flight);
	}
	public void deleteFlight(Flight flight) throws RemoteException {
		stub.deleteFlight(this.clientName, flight);
	}

	public void startup(FlightClient client) throws RemoteException {
		Display display = new Display();
		Shell shell = new Shell(display);
		//shell.setLayout(new GridLayout(1,false));
		
		ui = new FlightUI(shell,SWT.NONE);	
		//ui.setClient(client);
		ui.setFlights(flights);
		 
		 shell.pack();
		 shell.open();
		 while(!shell.isDisposed()) {
			 if(!display.readAndDispatch()) {
				 display.sleep();
			 }
			 if(display.isDisposed()) {
				 this.logout();
				 
			 }
		 }
		 
		 display.dispose();
		 
		 if(display.isDisposed()) {
			 this.logout();
			 
		 }
		 //this.logout();
		 
	}
	

	public static void main(String[] args) throws RemoteException {
		
		FlightClient client = new FlightClient(UUID.randomUUID().toString());
		try {
            Registry registry = LocateRegistry.getRegistry(null);
            IFlightServer stub = (IFlightServer) registry.lookup("Server");
            stub.login(client.clientName,client);
            client.setConnection(stub);
            
           
            //stub.logout(client.clientName);

        } catch (Exception e) {
             e.printStackTrace();
        } 
		client.startup(client);
		client.stub.logout(client.clientName);
		 
		
	}

}
