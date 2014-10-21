package se.berglund.userservice;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import se.berglund.model.User;

public final class UserRepository {

	private static final AtomicLong userIds = new AtomicLong();
	private static final Map<Long, User> users = new HashMap<>();

	public User addUser(User user) {
		User newUser = new User(getNewUserId(), user.getEmail(),
				user.getPassword());
		users.put(newUser.getId(), newUser);

		return newUser; 
	}
	
	public void updateUser(long id, User user){
		if (getUsers().containsKey(id)) {
			getUsers().put(id, user);
		}
	}

	public Long getNewUserId() {
		return userIds.incrementAndGet();
	}

	public Map<Long, User> getUsers() {
		return users;
	}
}
