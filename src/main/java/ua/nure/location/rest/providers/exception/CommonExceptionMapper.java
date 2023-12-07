package ua.nure.location.rest.providers.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.io.PrintWriter;
import java.io.StringWriter;

@Provider
public class CommonExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {
		System.out.println("toResponse() exception " + exception.getMessage());
		exception.printStackTrace();
		return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(getEntity(exception)).build();
	}

	private int getStatusCode(Throwable exception) {
		if (exception instanceof WebApplicationException) {
			return ((WebApplicationException) exception).getResponse().getStatus();
		}
		return Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
	}

	private Object getEntity(Throwable exception) {
		StringWriter errorMsg = new StringWriter();
		exception.printStackTrace(new PrintWriter(errorMsg));
		return errorMsg.toString();
	}
}