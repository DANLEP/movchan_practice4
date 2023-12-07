package ua.nure.location.rest.providers;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import ua.nure.location.entity.Place;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Produces(MediaType.APPLICATION_XML)
@Consumes(MediaType.APPLICATION_XML)
public class PlaceXmlProvider implements MessageBodyReader<Place>, MessageBodyWriter<Place> {

	private final JAXBContext jaxbContext;

	public PlaceXmlProvider() {
		try {
			this.jaxbContext = JAXBContext.newInstance(Place.class);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
		return Place.class.isAssignableFrom(aClass);
	}

	@Override
	public Place readFrom(Class<Place> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> multivaluedMap,
			InputStream inputStream) throws IOException, WebApplicationException {
		try {
			return (Place) jaxbContext.createUnmarshaller().unmarshal(inputStream);
		} catch (JAXBException e) {
			return null;
		}
	}

	@Override
	public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
		return Place.class.isAssignableFrom(aClass);
	}

	@Override
	public void writeTo(Place place, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap,
			OutputStream outputStream) throws IOException, WebApplicationException {
		try {
			jaxbContext.createMarshaller().marshal(place, outputStream);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
}
