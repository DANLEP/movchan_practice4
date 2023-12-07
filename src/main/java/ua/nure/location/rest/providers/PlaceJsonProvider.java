package ua.nure.location.rest.providers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import ua.nure.location.entity.Place;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PlaceJsonProvider implements MessageBodyReader<Place>, MessageBodyWriter<Place> {

	private final ObjectMapper objectMapper;

	public PlaceJsonProvider() {
		ContextResolver<ObjectMapper> mapperContext = new CommonJsonProvider();
		objectMapper = mapperContext.getContext(ObjectMapper.class);
	}

	@Override
	public boolean isReadable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
		return Place.class.isAssignableFrom(aClass);
	}

	@Override
	public Place readFrom(Class<Place> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> multivaluedMap,
			InputStream inputStream) throws IOException, WebApplicationException {
		return objectMapper.readValue(inputStream, Place.class);
	}

	@Override
	public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
		return Place.class.isAssignableFrom(aClass);
	}

	@Override
	public void writeTo(Place place, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> multivaluedMap,
			OutputStream outputStream) throws IOException, WebApplicationException {
		objectMapper.writeValue(outputStream, place);
	}
}

