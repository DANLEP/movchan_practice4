package ua.nure.location.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.MatrixParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.apache.commons.lang3.StringUtils;
import ua.nure.location.entity.Place;
import ua.nure.location.server.dao.PlaceDAO;
import ua.nure.location.server.dao.PlaceDAOInMemoryImpl;

import java.util.List;

public class PlaceSearcher {
	private PlaceDAO placeDAO = PlaceDAOInMemoryImpl.instance();

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Place> search(@MatrixParam("title") String title, @MatrixParam("tag_name") String tagName) {
		List<Place> filteredPlaces = placeDAO.listPlaces().stream().toList();

		if (StringUtils.isNotBlank(title)) {
			filteredPlaces = filteredPlaces.stream()
					.filter(place -> StringUtils.containsIgnoreCase(place.getTitle(), title))
					.toList();
		}

		if (StringUtils.isNotBlank(tagName)) {
			filteredPlaces = filteredPlaces.stream()
					.filter(place -> place.getTags().getTag()
							.stream()
							.anyMatch(tag -> tag.equalsIgnoreCase(tagName)))
					.toList();
		}

		return filteredPlaces;
	}

}
