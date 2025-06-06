package com.role_based_authentication_notes.roleBasedNotesTaker.dto;

import java.util.HashSet;
import java.util.Set;

import com.role_based_authentication_notes.roleBasedNotesTaker.model.Address;
import com.role_based_authentication_notes.roleBasedNotesTaker.model.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {
	
    @NotBlank(message = "Name is required", groups = {OnCreate.class, OnUpdate.class})
	private String name;
	
    @NotBlank(message = "Email is required", groups = {OnCreate.class, OnUpdate.class})
    @Email(message = "Invalid email format", groups = {OnCreate.class, OnUpdate.class})
	private String email;
	
	@NotBlank(message = "Password is required")
	private String password;
	
	 @NotBlank(message = "Password is required", groups = OnCreate.class) // only on create
	 @Size(min = 6, message = "Password must be at least 6 characters", groups = OnCreate.class)
	 private String address;
	
	private Set<Long> roles = new HashSet<>();
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Set<Long> getRoles() {
		return roles;
	}
	public void setRoles(Set<Long> roles) {
		this.roles = roles;
	}
	@Override
	public String toString() {
		return "UserDTO [name=" + name + ", email=" + email + ", password=" + password + ", address=" + address
				+ ", roles=" + roles + "]";
	}
	
}
