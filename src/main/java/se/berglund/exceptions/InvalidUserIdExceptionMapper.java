package se.berglund.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidUserIdExceptionMapper implements
		ExceptionMapper<InvalidUserIdException> {

	@Override
	public Response toResponse(InvalidUserIdException exception) {
		return Response.status(Status.NOT_FOUND)
				.entity("Invalid user id: " + exception.getMessage()).build();
	}
}
