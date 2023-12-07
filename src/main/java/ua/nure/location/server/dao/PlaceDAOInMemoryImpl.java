package ua.nure.location.server.dao;

import jakarta.xml.bind.JAXBException;
import org.xml.sax.SAXException;
import ua.nure.dbtable.DBTable;
import ua.nure.dbtable.DBTableFactory;
import ua.nure.dbtable.Filter;
import ua.nure.location.entity.Location;
import ua.nure.location.entity.Place;
import ua.nure.location.rest.exceptions.ValidationException;
import ua.nure.location.server.parser.Const;
import ua.nure.location.server.parser.JAXBParser;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

public class PlaceDAOInMemoryImpl implements PlaceDAO {

    DBTable<Place> places = DBTableFactory.instance();

    private static PlaceDAOInMemoryImpl instance;

    private PlaceDAOInMemoryImpl() {
        initPlaces();
    }

    public static synchronized PlaceDAOInMemoryImpl instance() {
        if (instance == null) {
            instance = new PlaceDAOInMemoryImpl();
        }
        return instance;
    }

    @Override
    public int addPlace(Place item) throws ValidationException {
        if (item == null) {
            throw new ValidationException("create: item is required");
        }
        int id = places.insert(item);
        item.setId(id);
        places.update(id, item);
        return id;
    }

    @Override
    public Place deletePlace(Integer id) throws ValidationException {
        if (id == null) {
            throw new ValidationException("delete: id is required");
        }
        return places.delete(id);
    }

    @Override
    public boolean updatePlaceDescription(int id, String description) throws ValidationException {
        Place p;
        p = places.get(id);
        if (description.length() < 10 || description.length() > 100)
            throw new ValidationException("Description Length must be from 10 to 100 letters");
        p.setDescription(description);
        return places.update(id, p);
    }

    Filter<Place> typeFilter = new Filter<>() {
        @Override
        public boolean accept(Place item, Object pattern) {
            String p = (String) pattern;
            return item.getType().toUpperCase().contains(p.toUpperCase());
        }
    };

    @Override
    public Collection<Place> findByActivityType(String pattern) throws ValidationException {
        if (pattern.isBlank() || pattern.isEmpty())
            throw new ValidationException("parameter error");
        return places.filter(pattern, typeFilter);
    }

    @Override
    public Collection<Place> listPlaces() {
        return places.selectAll();
    }

    @Override
    public Place findById(Integer id) throws ValidationException  {
        if (id == null) {
            throw new ValidationException("get: id is required");
        }
        Place place = places.get(id);
        if (place == null)
            throw new ValidationException("no such place with this id");
        return place;
    }

    @Override
    public Boolean updatePlace(Place place) throws ValidationException  {
        if (place == null || place.getId() == null) {
            throw new ValidationException("update: id is required");
        }
        return places.update(place.getId()
                .intValue(), place);
    }

    private void initPlaces(){
        try {
            List<Location> locations1 = JAXBParser.loadLocations(Const.XML_FILE, Const.XSD_FILE, Const.OBJECT_FACTORY)
            .getLocation();

            for (int i = 0; i < locations1.size(); i++) {
                List<Place> places1 = locations1.get(i).getPlaces().getPlace();
                for (int y = 0; y < locations1.get(i).getPlaces().getPlace().size(); y++){
                    addPlace(places1.get(y));
                }
            }
        } catch (JAXBException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
