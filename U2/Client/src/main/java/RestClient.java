import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.SyncInvoker;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.glassfish.jersey.client.ClientConfig;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import rest.FlightsList;
import soap.ReservationBookingService;

public class RestClient implements ClientInterface{
	public String clintID;
	public Shell shell;
	public WebTarget server;
	public Client client;
	public  RestClient(String id){
		this.clintID=id;
	}
	public boolean book(int day,int index,int row,int clone,String meal) {	
				
		Form form = new Form();
                form.param("day", String.valueOf(day));
                form.param("index", String.valueOf(index));
                form.param("row", String.valueOf(row));
                form.param("clone", String.valueOf(clone));
                form.param("meal", meal);
	    Response response = server.path("/rest").path("/book")
	              .request().post(Entity.form(form));
	      String success = response.readEntity(String.class);
	      if(success.equals("true")) {
	    	  return true;
	      }
	      return false;
	}
	
	@Override
	public HashMap<Integer, ArrayList<String>> getFlights() {
		String response = server.path("rest").
                 path("flights").
                 request().
                 accept(MediaType.APPLICATION_JSON).
                 get(String.class);
		FlightsList flightsList = JSON.parseObject(response, FlightsList.class);
		HashMap<Integer, String> flights = flightsList.flightsForOneWeek;
		HashMap<Integer, ArrayList<String>> flightsForOneWeek= new HashMap<Integer, ArrayList<String>>();
		for(int i =0 ;i<flights.size();i++) {
			String oneDayInString=flights.get(i);
			ArrayList<String> flightsForOneDay = JSON.parseObject(oneDayInString, ArrayList.class);
			flightsForOneWeek.put(i, flightsForOneDay);
		}
		return flightsForOneWeek;
	}
	public void setServer(WebTarget target) {
		this.server=target;
	}

	
	public void runClient() {
		//this.flightsForOneWeek = this.server.getFlights();
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
	
	private static URI getURI(String s) {
        return UriBuilder.fromUri(s).build();
    }
	
	public static void main(String[] args)  {
		//ClientConfig config = new ClientConfig();
//        Client client = ClientBuilder.newClient(config);
//        WebTarget target = client.target(getURI("http://localhost:8080/"));
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/");
        RestClient restClient = new RestClient(UUID.randomUUID().toString());
        restClient.setServer(target);
        restClient.runClient();
        
    }

	
	
}
