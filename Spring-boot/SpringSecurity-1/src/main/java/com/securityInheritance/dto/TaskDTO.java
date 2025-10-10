package com.securityInheritance.dto;

import java.time.LocalDate;

import com.securityInheritance.model.User;

import jakarta.persistence.ManyToOne;

public class TaskDTO {
	private String title;
	private String description;
	private LocalDate dueDate;
	private String priority;
	private boolean completed;

	private User owner;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public TaskDTO(String title, String description, LocalDate dueDate, String priority, boolean completed,
			User owner) {
		super();
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.priority = priority;
		this.completed = completed;
		this.owner = owner;
	}

	public TaskDTO() {
		super();
	}
	
	
}
