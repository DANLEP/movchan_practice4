package ua.nure.location.server.service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.ws.Holder;
import ua.nure.location.entity.Place;
import ua.nure.location.server.dao.DAOException;

import java.util.Collection;

@WebService(targetNamespace= Const.SERVICE_NS)
public interface PlaceService {

    @WebMethod()
    @WebResult(targetNamespace="http://location.nure.ua/entity")
    public Place getPlace(
            @WebParam(name="id")
            int id) throws DAOException;

    @WebMethod()
    public int addPlace(
            @WebParam(name="place", targetNamespace="http://location.nure.ua/entity")
            Place place) throws DAOException;

    @WebMethod()
    @WebResult(targetNamespace="http://location.nure.ua/entity")
    public Place deletePlace(
            @WebParam(name="id")
            int id,
            @WebParam(name="clientToken", header = true)
            String clientToken,
            @WebParam(name="serverToken", header = true, mode = WebParam.Mode.OUT)
            Holder<String> serverToken
    ) throws DAOException;


    @WebMethod()
    @WebResult(targetNamespace="http://location.nure.ua/entity")
    public Collection<Place> listPlaces();

    @WebMethod()
    @WebResult(targetNamespace="http://location.nure.ua/entity")
    public Collection<Place> findByActivityType(
            @WebParam(name="pattern")
            String pattern);
}
