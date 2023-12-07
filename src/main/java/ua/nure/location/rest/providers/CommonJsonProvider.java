package ua.nure.location.rest.providers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CommonJsonProvider implements ContextResolver<ObjectMapper> {
	private final ObjectMapper objectMapper;

	public CommonJsonProvider() {
		objectMapper = new ObjectMapper();
		// pretty print
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		// catch failed cases
		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		objectMapper.disable(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE);
	}

	@Override
	public ObjectMapper getContext(Class<?> aClass) {
		return objectMapper;
	}
}
