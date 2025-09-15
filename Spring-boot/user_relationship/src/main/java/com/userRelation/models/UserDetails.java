package com.userRelation.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_details")
public class UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "details_id")
	private long id;
	
	private int phoneNumder;
	private String email;
	private int salary;
	
	@OneToOne(mappedBy = "user_details")
	@JoinColumn(name = "user_id")
	private User user;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPhoneNumder() {
		return phoneNumder;
	}

	public void setPhoneNumder(int phoneNumder) {
		this.phoneNumder = phoneNumder;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserDetails(long id, int phoneNumder, String email, int salary, User user) {
		super();
		this.id = id;
		this.phoneNumder = phoneNumder;
		this.email = email;
		this.salary = salary;
		this.user = user;
	}

	public UserDetails() {
		super();
	}
	
	
}
