package interfaces;
import java.rmi.Remote;
import java.rmi.RemoteException;

import model.Flight;


public interface IFlightServer extends Remote{
	

	public void login(String clientName, IFlightClient client) throws RemoteException;

	public void logout(String clientName) throws RemoteException;

	public void updateFlight(String clientName, Flight flight)throws RemoteException;
	
	public void deleteFlight(String clientName, Flight flight) throws RemoteException;
	
}
