package com.student.UserCrud.dto;

public class UserDTO {

	private String name;
	private String Email;
	private String Password;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	@Override
	public String toString() {
		return "UserDTO [name=" + name + ", Email=" + Email + ", Password=" + Password + "]";
	}
	
}
