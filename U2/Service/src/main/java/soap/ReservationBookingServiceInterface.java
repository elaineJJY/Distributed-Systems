package soap;

import java.util.HashMap;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import Flight.Flight;

//Service Endpoint Interface
@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ReservationBookingServiceInterface {

	
	/**
     * @return returns HashMap<Integer, List<Flight>>
     */
	@WebMethod (operationName = "getFlights" )
	@WebResult(partName = "return")
	HashMap<Integer, List<Flight>> getFlights();
	
	
	@WebMethod boolean getBookingResult();
}
