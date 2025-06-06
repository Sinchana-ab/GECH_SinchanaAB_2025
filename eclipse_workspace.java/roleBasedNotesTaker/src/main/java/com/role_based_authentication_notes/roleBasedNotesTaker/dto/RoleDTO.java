package com.role_based_authentication_notes.roleBasedNotesTaker.dto;

import jakarta.validation.constraints.NotBlank;

public class RoleDTO {
	@NotBlank(message = "Role name is required.")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "RoleDTO [name=" + name + "]";
	}
	
	
}
