package com.user.form.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserDTO {
	
	@NotBlank(message = "name is required")
	private String name;
	@NotBlank(message = "email is required")
	@Email(message = "enter email within the formate ")
	private String email;
	@NotNull(message = "enter the age")
	@Min(value = 18, message = "it should be above 18 ")
	private int age;
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public UserDTO(String name, String email, int age) {
		super();
		this.name = name;
		this.email = email;
		this.age = age;
	}
	public UserDTO() {
		super();
	}
	
	
}
