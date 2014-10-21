package se.berglund.userservice;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import se.berglund.exceptions.InvalidUserFormatException;
import se.berglund.exceptions.InvalidUserIdException;
import se.berglund.model.User;

@Path("/users")
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public final class UserResource {

	@Context
	private UriInfo uriInfo;

	private static final UserRepository USER_REPOSITORY = new UserRepository();

	@POST
	public Response addUser(User user) {
		if (user.getEmail() != null && user.getPassword() != null) {

			User newUser = USER_REPOSITORY.addUser(user);
			final URI location = uriInfo.getAbsolutePathBuilder()
					.path(newUser.getId().toString()).build();

			return Response.created(location).build();
		} else {
			throw new InvalidUserFormatException(
					"A user must contain email and password");
		}
	}

	@GET
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

		GenericEntity<Collection<User>> users = new GenericEntity<Collection<User>>(
				usersList) {
		};

		return Response.status(Status.OK).entity(users).build();
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
	public Response updateUser(@PathParam("id") Long id, User user) {
		if (USER_REPOSITORY.getUsers().containsKey(id)) {
			if (user.getEmail() != null && user.getPassword() != null) {
				USER_REPOSITORY.updateUser(id, user);
				return Response.status(Status.NO_CONTENT).build();
			} else {
				throw new InvalidUserFormatException(
						"User must contain email and password");
			}
		}
		throw new InvalidUserIdException(id);
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
