package com.storeeverythin.registration;

public class RegistrationRequest {

	private final String firstName;
	private final String lastName;
	private final String username;
	private final String password;
	private final String age;
	
	
	
	public RegistrationRequest(String firstName, String lastName, String username, String password, String age) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.age = age;
	}
	
	
	@Override
	public String toString() {
		return "RegistrationService [firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", password=" + password + ", age=" + age + "]";
	}

	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getAge() {
		return age;
	}
}
