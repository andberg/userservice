package se.berglund.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public final class User {

	@XmlElement
	private final Long id;
	
	@XmlElement
	private final String email;
	
	@XmlElement
	private final String password;
	
	@SuppressWarnings("unused")
	private User(){
		this(null, null, null);
	}

	public User(Long id, String email, String password) {
		this.id = id;
		this.email = email;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return " email: " + email + ", password: " + password;
	}
}
