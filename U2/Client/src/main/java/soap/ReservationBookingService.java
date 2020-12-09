package soap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.bind.annotation.XmlElement;

import Flight.Flight;

//Service Endpoint Interface
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ReservationBookingService {

	/**
     * @return returns HashMap<Integer, List<Flight>>
     */
	@WebMethod (operationName = "getFlights" )
	@WebResult(partName = "return")
	public HashMap<Integer, ArrayList<String>> getFlights();
	

	@WebMethod(operationName = "book" )
	public @WebResult(partName = "success")
	boolean book(@WebParam(name = "day", partName = "day",targetNamespace="http://soap/")int day,
			@WebParam(name = "index", partName = "index",targetNamespace="http://soap/")int index,
			@WebParam(name = "row", partName = "row",targetNamespace="http://soap/")int row,
			@WebParam(name = "clone", partName = "clone",targetNamespace="http://soap/")int clone,
			@WebParam(name = "meal", partName = "meal",targetNamespace="http://soap/")String meal);
	

	
	@WebMethod (operationName = "test" )
	@WebResult(partName = "test") 
	String test();
}
