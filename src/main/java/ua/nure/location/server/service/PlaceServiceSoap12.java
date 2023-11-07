package ua.nure.location.server.service;

import jakarta.jws.HandlerChain;
import jakarta.jws.WebService;
import jakarta.xml.ws.BindingType;
import jakarta.xml.ws.soap.SOAPBinding;

@WebService(
        endpointInterface = "ua.nure.location.server.service.PlaceService",
        serviceName = "Places",
        portName = "PlacePortSoap12"
)
@HandlerChain(file = "handlers.xml")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class PlaceServiceSoap12 extends PlaceServiceImpl {

}
