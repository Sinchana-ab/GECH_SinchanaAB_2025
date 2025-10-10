package com.securityInheritance.dto;

import java.util.List;

import com.securityInheritance.model.Task;

import jakarta.persistence.OneToMany;

public class UserDTO {
	private String username;
	private String password;
	private String role;
	
	private List<Task> tasks;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public UserDTO(String username, String password, String role, List<Task> tasks) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
		this.tasks = tasks;
	}

	public UserDTO() {
		super();
	}
	
	
}
