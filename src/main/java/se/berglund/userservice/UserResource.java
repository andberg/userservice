package se.berglund.userservice;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import se.berglund.exceptions.InvalidUserIdException;
import se.berglund.model.User;

@Path("/users")
@Consumes("text/plain")
@Produces("text/plain")
public final class UserResource {

	@Context
	private UriInfo uriInfo;

	private static final UserRepository USER_REPOSITORY = new UserRepository();

	@POST
	public Response addUser(String userAsString) {

		final User user = USER_REPOSITORY.decodeUserFromCommaSeparatedString(
				null, userAsString);
		final URI location = uriInfo.getAbsolutePathBuilder()
				.path(user.getId().toString()).build();

		USER_REPOSITORY.getUsers().put(user.getId(), user);

		return Response.created(location).build();
	}

	@GET
	@Produces("text/html")
	public Response getUsers(
			@QueryParam("sorted-by") @DefaultValue("desc") String sortedBy) {

		List<User> usersList = new ArrayList<User>(USER_REPOSITORY.getUsers()
				.values());
		if (sortedBy.equals("asc")) {
			Collections.sort(usersList, new UsersSortedByNameAsAscComparator());
		} else {
			Collections
					.sort(usersList, new UsersSortedByNameAsDescComparator());
		}

		StringBuilder userRows = new StringBuilder();

		userRows.append("<head><meta charset=utf8></head>");

		for (User user : usersList) {
			userRows.append("<a href=" + uriInfo.getAbsolutePath() + "/" + user.getId()
					+ ">" + user.getId() + ", " + user.getEmail() + "</a></br>");
		}

		
		return Response.status(Status.OK).entity(userRows.toString()).build();
	}

	@GET
	@Path("{id}")
	public Response getUser(@PathParam("id") Long id) {
		if (USER_REPOSITORY.getUsers().containsKey(id)) {

			final User user = USER_REPOSITORY.getUsers().get(id);
			return Response.status(Status.OK).entity(user.toString()).build();
		} else {
			throw new InvalidUserIdException(id);
		}
	}

	@PUT
	@Path("{id}")
	public Response updateUser(@PathParam("id") Long id, String userAsString) {
		if (USER_REPOSITORY.getUsers().containsKey(id)) {
			User user = USER_REPOSITORY.decodeUserFromCommaSeparatedString(id,
					userAsString);
			USER_REPOSITORY.getUsers().put(id, user);
			return Response.status(Status.NO_CONTENT).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}

	@DELETE
	@Path("{id}")
	public Response deleteUser(@PathParam("id") Long id) {
		if (USER_REPOSITORY.getUsers().containsKey(id)) {
			USER_REPOSITORY.getUsers().remove(id);
			return Response.status(Status.OK).entity(id).build();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
}
