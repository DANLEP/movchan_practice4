package ua.nure.location.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ua.nure.location.entity.Place;
import ua.nure.location.server.dao.PlaceDAO;
import org.apache.commons.lang3.StringUtils;
import ua.nure.location.server.dao.PlaceDAOInMemoryImpl;

@Path(Constants.PLACES_SERVICE_PATH)
public class PlaceRestService {
    private PlaceDAO placeDAO = PlaceDAOInMemoryImpl.instance();

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getPlacesByActivityType(@QueryParam("activity_type") String activityType){
        if (StringUtils.isBlank(activityType)) {
            return Response.ok(placeDAO.listPlaces())
                    .build();
        }
        else {
            return Response.ok(placeDAO.findByActivityType(activityType))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getPlaceById(@PathParam("id") Integer id) {
        return Response.ok(placeDAO.findById(id)).build();
    }

    @Path("/search")
    public PlaceSearcher searchPlace() {
        return new PlaceSearcher();
    }

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createPlace(Place place) {
        return Response.ok(placeDAO.addPlace(place)).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updatePlace(Place place) {
        return Response.ok(placeDAO.updatePlace(place)).build();
    }

    @DELETE
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Response deletePlace(@PathParam("id") Integer id) {
        return Response.ok(placeDAO.deletePlace(id)).build();
    }
}
