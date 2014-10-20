package se.berglund.userservice;

import java.util.Comparator;

import se.berglund.model.User;

public class UsersSortedByNameAsDescComparator implements Comparator<User>{

	@Override
	public int compare(User user, User other) {
		return other.getEmail().compareTo(user.getEmail());
	}
}
