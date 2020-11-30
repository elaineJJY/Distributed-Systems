import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Logger;

import interfaces.IFlightClient;
import interfaces.IFlightServer;
import model.Flight;

public class FlightServer implements IFlightServer{

	private static Logger logger = Logger.getLogger(FlightServer.class.getName());
	private  List<Flight> flights = new ArrayList(); 
	private  Map<String, IFlightClient> Clients= new HashMap<String, IFlightClient>();
	private  Map<String, Flight> flightsmap= new HashMap<String, Flight>();
	protected FlightServer() throws RemoteException {
		super();

		// initialize with some flights
		Flight f = new Flight();
		f.init("LH","591");
		flights.add(f);
		
		Flight f2 = new Flight();
		f2.init("MU", "380");
		flights.add(f2);
		
		Flight f3 = new Flight();
		f3.init("CA", "586");
		flights.add(f3);
		
		updateMap();
	}

	/**
	 * does a client login
	 */
	@Override
	public void login(String clientName, IFlightClient client) throws RemoteException {
		this.Clients.put(clientName,client);
		logger.log(Level.INFO, "New client logged in: " + clientName);
		client.receiveListOfFlights(this.flights);
	}

	@Override
	public void logout(String clientName)throws RemoteException {
		//System.out.println(clientName);
		this.Clients.remove(clientName);
		logger.log(Level.INFO, "Client logged out: " + clientName);
	}

	@Override
	public void updateFlight(String clientName, Flight flight)throws RemoteException {
		flightsmap.put(flight.id,flight);
		updatelist();
		informAllClients(flight,false);	
		logger.log(Level.INFO, "Update flight: " + flight.toString());
	}

	@Override
	public void deleteFlight(String clientName, Flight flight) throws RemoteException{
		informAllClients(flight,true);
		removeFlight(flight);
		logger.log(Level.INFO, "Delete flight: " + flight.toString());
	}
	
	private void informAllClients(Flight flight, boolean deleted)throws RemoteException {

		Iterator entries = Clients.entrySet().iterator();
 
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			IFlightClient client= (IFlightClient) entry.getValue();
			client.receiveUpdatedFlight(flight,deleted);
		}
	}
	
	public void updateMap() {
		flightsmap.clear();
		for(int i = 0; i<flights.size();i++) {
			Flight f = flights.get(i);
			flightsmap.put(f.id,f);
		}
	}
	
	public void updatelist() {
		flights.clear(); 
		for (String id : flightsmap.keySet()) {
            flights.add(flightsmap.get(id));
        }
	}
	
	public void removeFlight(Flight f) {
		flightsmap.remove(f.id);
		updatelist();
	}
	
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		try {
			//netstat -ano|findstr 1099
			//taskkill -f -pid 4836
			
			// generate local registry
			Registry registry = LocateRegistry.createRegistry(1099);
			
			// generate game server
			FlightServer server =  new FlightServer();
			IFlightServer stub = (IFlightServer) UnicastRemoteObject.exportObject(server, 1099);
			
			registry.bind("Server", stub);
			
			logger.info("Server is ready");
		} catch (Exception ex) {
			logger.log(Level.SEVERE, "Server exception", ex);
		}
	}

}
