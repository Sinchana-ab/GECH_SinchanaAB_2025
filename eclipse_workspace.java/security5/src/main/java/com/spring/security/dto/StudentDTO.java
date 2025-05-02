package com.spring.security.dto;

public class StudentDTO {
	
	private String name;
	private String email;
	private String password;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
//	public StudentDTO() {
//		super();
//		this.name = name;
//		this.email = email;
//		this.password = password;
//	}
	
	@Override
	public String toString() {
		return "StudentDTO [name=" + name + ", email=" + email + ", password=" + password + "]";
	}
public StudentDTO() {
	super();
}
	
}
