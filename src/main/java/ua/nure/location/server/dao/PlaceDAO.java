package ua.nure.location.server.dao;

import ua.nure.location.entity.Place;

import java.util.Collection;

public interface PlaceDAO {
    public int addPlace(Place item) throws DAOException;

    public Place deletePlace(int id) throws DAOException;

    public boolean updatePlaceDescription(int id, String description) throws DAOException;

    public Collection<Place> findByActivityType(String pattern);

    public Collection<Place> listPlaces();
    public Place findById(Integer id) throws DAOException;
}
