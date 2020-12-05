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
	@XmlElement(required=true, nillable=true)
	HashMap<Integer, ArrayList<Flight>> getFlights();
	
//    /**
//     * 
//     * @param day
//     * @param index
//     * @param row
//     * @param clone
//     * @param meal
//     * @return
//     *     returns boolean
//     */
//	@WebMethod(operationName = "book" )
//	@WebResult(partName = "return")
//	@XmlElement(required=true, nillable=true)
//	boolean book(@WebParam(name = "day", partName = "day")int day,
//			@WebParam(name = "index", partName = "index")int index,
//			@WebParam(name = "row", partName = "row")int row,
//			@WebParam(name = "clone", partName = "clone")int clone,
//			@WebParam(name = "meal", partName = "meal")String meal);
	
	
	@WebMethod (operationName = "test" )
	@WebResult(partName = "return")
	@XmlElement(required=true, nillable=true)
	Flight test();
}
