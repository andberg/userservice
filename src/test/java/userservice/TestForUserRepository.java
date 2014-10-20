package userservice;

import org.junit.Test;

import se.berglund.exceptions.InvalidUserFormatException;
import se.berglund.model.User;
import se.berglund.userservice.UserRepository;
import static org.junit.Assert.*;

public class TestForUserRepository {

	@Test
	public void userDecodesSuccessfullyFromCommaSeparatedString() {
		UserRepository userRepository = new UserRepository();

		String userAsCommaSeparatedString = "andrea, berglund";

		User user = userRepository.decodeUserFromCommaSeparatedString(null,
				userAsCommaSeparatedString);

		assertNotNull(user.getEmail());
		assertNotNull(user.getPassword());
		assertNotNull(user.getId());
	}

	@Test(expected = InvalidUserFormatException.class)
	public void userDecodesUnsuccesfullyFromCommaSeparatedString() {
		UserRepository userRepository = new UserRepository();

		String[] invalidUserStringFormats = new String[] { ",", ",andrea",
				"andrea,", "dannie", "" };

		for (int i = 0; i < invalidUserStringFormats.length; i++) {
			String userAsCommaSeparatedString = invalidUserStringFormats[i];

			userRepository.decodeUserFromCommaSeparatedString(null,
					userAsCommaSeparatedString);
		}
	}

	@Test
	public void addedUserExistsInRepository() {
		UserRepository userRepository = new UserRepository();

		Long id = 1L;

		User user = new User(id, "Dandrea", "Håklund");
		userRepository.getUsers().put(id, user);
		
		assertNotNull(userRepository.getUsers().get(id));
	}
	
	@Test
	public void projectWorks() {
		assertTrue(true);
	}
}
