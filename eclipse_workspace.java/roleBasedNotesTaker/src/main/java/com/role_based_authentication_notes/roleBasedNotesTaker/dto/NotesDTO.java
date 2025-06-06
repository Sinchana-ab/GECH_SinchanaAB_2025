package com.role_based_authentication_notes.roleBasedNotesTaker.dto;

import jakarta.validation.constraints.NotBlank;

public class NotesDTO {
	@NotBlank(message = "Notes title is required")
	private String title;
	
	@NotBlank(message = "Notes content is required")
	private String content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
