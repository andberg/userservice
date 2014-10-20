package se.berglund.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public final class InvalidUserFormatExceptionMapper implements
		ExceptionMapper<InvalidUserFormatException> {

	@Override
	public Response toResponse(InvalidUserFormatException exception) {
		return Response.status(Status.BAD_REQUEST)
				.entity("Bad user format for: " + exception.getMessage())
				.build();
	}
}
