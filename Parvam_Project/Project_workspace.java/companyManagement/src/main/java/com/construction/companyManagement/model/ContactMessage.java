package com.construction.companyManagement.model;

import jakarta.persistence.Entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class ContactMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String subject;

    @Column(length = 2000)
    private String message;

    private LocalDate submittedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDate getSubmittedAt() {
		return submittedAt;
	}

	public void setSubmittedAt(LocalDate submittedAt) {
		this.submittedAt = submittedAt;
	}

	public ContactMessage(Long id, String name, String email, String subject, String message, LocalDate submittedAt) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.subject = subject;
		this.message = message;
		this.submittedAt = submittedAt;
	}

	public ContactMessage() {
		super();
	}

	@Override
	public String toString() {
		return "ContactMessage [id=" + id + ", name=" + name + ", email=" + email + ", subject=" + subject
				+ ", message=" + message + ", submittedAt=" + submittedAt + "]";
	}
    
}

