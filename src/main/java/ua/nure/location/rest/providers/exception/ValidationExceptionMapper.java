package ua.nure.location.rest.providers.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import ua.nure.location.server.service.handled.ValidationException;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
	@Override
	public Response toResponse(ValidationException exception) {
		System.out.println("toResponse() exception " + exception.getMessage());
		exception.printStackTrace();

		return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(exception.getMessage()).build();
	}

}
