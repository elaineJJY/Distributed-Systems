package soap;


import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "SoapService", targetNamespace = "http://soap.com/", wsdlLocation = "http://localhost:8090/bookingservice")
public class SoapService extends Service{

    private final static URL WSDL_LOCATION;

    static {
        URL url = null;
        try {
            url = new URL("http://localhost:9999/ws/hello?wsdl");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public SoapService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SoapService() {
    	super(WSDL_LOCATION, 
            new QName("http://ws.mkyong.com/", "HelloWorldImplService"));
    }

//    /**
//     * 
//     * @return
//     *     returns HelloWorld
//     */
//    @WebEndpoint(name = "HelloWorldImplPort")
//    public HelloWorld getHelloWorldImplPort() {
//        return (HelloWorld)super.getPort(
//            new QName("http://ws.mkyong.com/", "HelloWorldImplPort"), 
//            HelloWorld.class);
//    }
//
//    /**
//     * 
//     * @param features
//     *  A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  
//     *  Supported features not in the <code>features</code> parameter will have their default values.
//     * @return
//     *  returns HelloWorld
//     */
//    @WebEndpoint(name = "HelloWorldImplPort")
//    public HelloWorld getHelloWorldImplPort(WebServiceFeature... features) {
//        return (HelloWorld)super.getPort(
//            new QName("http://ws.mkyong.com/", "HelloWorldImplPort"), 
//            HelloWorld.class, 
//            features);
//    }

}