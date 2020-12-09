import java.net.URI;
import java.rmi.RemoteException;

import javax.xml.ws.Endpoint;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import soap.ReservationBookingServiceImpl;

public class ServerMain {
	


	public static void main(String[] args) throws RemoteException  {
		//star Restful
		ResourceConfig rc = new ResourceConfig().packages("rest"); 
	    JdkHttpServerFactory.createHttpServer(URI.create("http://localhost:8080/"),rc);
	    
	    //star Soap
	    Endpoint.publish("http://localhost:8090/bookingservice", new ReservationBookingServiceImpl()); 
	}

}