package ua.nure.location.server.dao;

import ua.nure.location.entity.Place;

import java.sql.SQLException;
import java.util.Collection;

public interface PlaceDAO {
    public int addPlace(Place item);

    public Place deletePlace(Integer id);

    public boolean updatePlaceDescription(int id, String description) throws DAOException;

    public Collection<Place> findByActivityType(String pattern);

    public Collection<Place> listPlaces();
    public Place findById(Integer id);

    public Boolean updatePlace(Place place);
}
