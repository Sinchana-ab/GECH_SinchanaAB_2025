package com.construction.companyManagement.dto;

public class UserDTO {
    private String username;
    private String email;
    private String password;
    private String role;
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserDTO(String username, String email, String password, String role) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}
	public UserDTO() {
		super();
	}
	@Override
	public String toString() {
		return "UserDTO [username=" + username + ", email=" + email + ", password=" + password + ", role=" + role + "]";
	}
	
	
    
    
}