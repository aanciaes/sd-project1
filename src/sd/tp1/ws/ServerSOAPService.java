
package sd.tp1.ws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "ServerSOAPService", targetNamespace = "http://soap.srv.sd/", wsdlLocation = "http://192.168.1.94:8081/ServerSoap?wsdl")
public class ServerSOAPService
    extends Service
{

    private final static URL SERVERSOAPSERVICE_WSDL_LOCATION;
    private final static WebServiceException SERVERSOAPSERVICE_EXCEPTION;
    private final static QName SERVERSOAPSERVICE_QNAME = new QName("http://soap.srv.sd/", "ServerSOAPService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://192.168.1.94:8081/ServerSoap?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SERVERSOAPSERVICE_WSDL_LOCATION = url;
        SERVERSOAPSERVICE_EXCEPTION = e;
    }

    public ServerSOAPService() {
        super(__getWsdlLocation(), SERVERSOAPSERVICE_QNAME);
    }

    public ServerSOAPService(WebServiceFeature... features) {
        super(__getWsdlLocation(), SERVERSOAPSERVICE_QNAME, features);
    }

    public ServerSOAPService(URL wsdlLocation) {
        super(wsdlLocation, SERVERSOAPSERVICE_QNAME);
    }

    public ServerSOAPService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SERVERSOAPSERVICE_QNAME, features);
    }

    public ServerSOAPService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ServerSOAPService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ServerSOAP
     */
    @WebEndpoint(name = "ServerSOAPPort")
    public ServerSOAP getServerSOAPPort() {
        return super.getPort(new QName("http://soap.srv.sd/", "ServerSOAPPort"), ServerSOAP.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ServerSOAP
     */
    @WebEndpoint(name = "ServerSOAPPort")
    public ServerSOAP getServerSOAPPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://soap.srv.sd/", "ServerSOAPPort"), ServerSOAP.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SERVERSOAPSERVICE_EXCEPTION!= null) {
            throw SERVERSOAPSERVICE_EXCEPTION;
        }
        return SERVERSOAPSERVICE_WSDL_LOCATION;
    }

}
