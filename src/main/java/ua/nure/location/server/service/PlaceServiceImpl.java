package ua.nure.location.server.service;

import jakarta.jws.HandlerChain;
import jakarta.jws.WebService;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.ws.Holder;
import org.xml.sax.SAXException;
import ua.nure.location.entity.Place;
import ua.nure.location.server.dao.DAOException;
import ua.nure.location.server.dao.PlaceDAO;
import ua.nure.location.server.dao.PlaceDAOInMemoryImpl;

import java.util.Collection;

@HandlerChain(file = "security_handler.xml")
@WebService(serviceName="Places",
        portName="PlacePort",
        endpointInterface="ua.nure.location.server.service.PlaceService",
        targetNamespace=Const.SERVICE_NS)
public class PlaceServiceImpl implements PlaceService {

    private static PlaceDAO placeDAO;

    static {
        try {
            placeDAO = PlaceDAOInMemoryImpl.instance();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Place getPlace(int id) throws DAOException {
        return placeDAO.findById(id);
    }

    @Override
    public int addPlace(Place place) throws DAOException {
        return placeDAO.addPlace(place);
    }

    @Override
    public Place deletePlace(int id, String clientToken, Holder<String> serverToken) throws DAOException {
        return placeDAO.deletePlace(id);
    }

    @Override
    public Collection<Place> listPlaces() {
        return placeDAO.listPlaces();
    }

    @Override
    public Collection<Place> findByActivityType(String pattern) {
        return placeDAO.findByActivityType(pattern);
    }
}
