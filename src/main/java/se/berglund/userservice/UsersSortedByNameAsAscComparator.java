package se.berglund.userservice;

import java.util.Comparator;

import se.berglund.model.User;

public final class UsersSortedByNameAsAscComparator implements Comparator<User> {

	@Override
	public int compare(User user, User other) {
		return user.getEmail().compareTo(other.getEmail());
	}
}
