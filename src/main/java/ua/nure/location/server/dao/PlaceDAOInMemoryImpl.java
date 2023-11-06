package ua.nure.location.server.dao;

import jakarta.xml.bind.JAXBException;
import org.xml.sax.SAXException;
import ua.nure.dbtable.DBTable;
import ua.nure.dbtable.DBTableFactory;
import ua.nure.dbtable.Filter;
import ua.nure.location.entity.Location;
import ua.nure.location.entity.Place;
import ua.nure.location.server.parser.Const;
import ua.nure.location.server.parser.JAXBParser;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class PlaceDAOInMemoryImpl implements PlaceDAO {

    DBTable<Place> places = DBTableFactory.instance();

    private static PlaceDAOInMemoryImpl instance;

    private PlaceDAOInMemoryImpl() throws JAXBException, SAXException {
        initPlaces();
    }

    public static synchronized PlaceDAOInMemoryImpl instance() throws JAXBException, SAXException {
        if (instance == null) {
            instance = new PlaceDAOInMemoryImpl();
        }
        return instance;
    }

    @Override
    public int addPlace(Place item) {
        int id = places.insert(item);
        item.setId(id);
        try {
            places.update(id, item);
        } catch (SQLException e) {
            // do nothing, always exist
        }
        return id;
    }

    @Override
    public Place deletePlace(int id) throws DAOException {
        try {
            return places.delete(id);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean updatePlaceDescription(int id, String description) throws DAOException {
        Place p;
        try {
            p = places.get(id);
        } catch (SQLException e) {
            throw new DAOException("ID isn't exists");
        }
        if (description.length() < 10 || description.length() > 100)
            throw new DAOException("Description Length must be from 10 to 100 letters");
        p.setDescription(description);
        try {
            return places.update(id, p);
        } catch (SQLException e) {
            throw new DAOException("Unsuccessful update");
        }
    }

    Filter<Place> typeFilter = new Filter<>() {
        @Override
        public boolean accept(Place item, Object pattern) {
            String p = (String) pattern;
            return item.getType().toUpperCase().contains(p.toUpperCase());
        }
    };

    @Override
    public Collection<Place> findByActivityType(String pattern) {
        return places.filter(pattern, typeFilter);
    }

    @Override
    public Collection<Place> listPlaces() {
        return places.selectAll();
    }

    @Override
    public Place findById(Integer id) throws DAOException {
        try {
            return places.get(id);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private void initPlaces() throws JAXBException, SAXException {
        List<Location> locations1 =
                JAXBParser.loadLocations(Const.XML_FILE, Const.XSD_FILE, Const.OBJECT_FACTORY)
                .getLocation();


        for (int i = 0; i < locations1.size(); i++) {
            List<Place> places1 = locations1.get(i).getPlaces().getPlace();
            for (int y = 0; y < locations1.get(i).getPlaces().getPlace().size(); y++){
                addPlace(places1.get(y));
            }
        }
    }
}
