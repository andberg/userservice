package se.berglund.userservice;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import se.berglund.exceptions.InvalidUserFormatException;
import se.berglund.model.User;

public final class UserRepository {

	private static final AtomicLong userIds = new AtomicLong();
	private static final Map<Long, User> users = new HashMap<>();

	public User decodeUserFromCommaSeparatedString(Long id, String userAsString) {
		String[] values = userAsString.split(",");

		if (values.length == 2 && !(userAsString.charAt(0) == ',')) {
			String email = values[0];
			String password = values[1];
			
			User user = null;
			if (id == null) {
				user = new User(getNewUserId(), email, password);
			} else {
				user = new User(id, email, password);
			}

			return user;
		} else {
			throw new InvalidUserFormatException(userAsString);
		}
	}

	public Long getNewUserId() {
		return userIds.incrementAndGet();
	}

	public Map<Long, User> getUsers() {
		return users;
	}
}
