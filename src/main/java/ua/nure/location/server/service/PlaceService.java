package ua.nure.location.server.service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.ws.Holder;
import ua.nure.location.entity.Place;
import ua.nure.location.server.dao.DAOException;
import ua.nure.location.server.service.handled.SecurityHeader;
import ua.nure.location.server.service.handled.ValidationException;

import java.util.Collection;

@WebService(targetNamespace= Const.SERVICE_NS)
public interface PlaceService {

    @WebMethod(operationName = "getPlace")
    @WebResult(name = "place", targetNamespace=Const.ENTITY_NS)
    public Place getPlace(
            @WebParam(name="id")
            @XmlElement(required = true)
            int id,
            @WebParam(name="clientToken", header = true, mode = WebParam.Mode.IN)
            SecurityHeader clientToken,
            @WebParam(name="serverToken", header = true, mode = WebParam.Mode.OUT)
            Holder<SecurityHeader> serverToken
    ) throws DAOException;

    @WebMethod(operationName = "addPlace")
    @WebResult(name = "placeId")
    public int addPlace(
            @WebParam(name="place", targetNamespace=Const.ENTITY_NS)
            @XmlElement(required = true)
            Place place,
            @WebParam(name="clientToken", header = true, mode = WebParam.Mode.IN)
            SecurityHeader clientToken,
            @WebParam(name="serverToken", header = true, mode = WebParam.Mode.OUT)
            Holder<SecurityHeader> serverToken
            ) throws DAOException;

    @WebMethod(operationName = "deletePlace")
    @WebResult(name = "place", targetNamespace=Const.ENTITY_NS)
    public Place deletePlace(
            @WebParam(name="id")
            @XmlElement(required = true)
            int id,
            @WebParam(name="clientToken", header = true, mode = WebParam.Mode.IN)
            SecurityHeader clientToken,
            @WebParam(name="serverToken", header = true, mode = WebParam.Mode.OUT)
            Holder<SecurityHeader> serverToken
    ) throws DAOException;


    @WebMethod(operationName = "listPlaces")
    @WebResult(name = "places", targetNamespace=Const.ENTITY_NS)
    public Collection<Place> listPlaces(
            @WebParam(name="clientToken", header = true, mode = WebParam.Mode.IN)
            SecurityHeader clientToken,
            @WebParam(name="serverToken", header = true, mode = WebParam.Mode.OUT)
            Holder<SecurityHeader> serverToken);

    @WebMethod(operationName = "findByActivityType")
    @WebResult(targetNamespace=Const.ENTITY_NS)
    public Collection<Place> findByActivityType(
            @WebParam(name="type")
            @XmlElement(required = true)
            String type,
            @WebParam(name="clientToken", header = true, mode = WebParam.Mode.IN)
            SecurityHeader clientToken,
            @WebParam(name="serverToken", header = true, mode = WebParam.Mode.OUT)
            Holder<SecurityHeader> serverToken) throws ValidationException;
}
