package com.userRelation.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long id;
	@Column(name = "user_name")
	private String name;
	
	@OneToOne
	@JoinColumn(name = "details_id")
	private UserDetails user_details;
	
	@OneToMany(mappedBy = "user")
	private List<Address> address;
	
	@ManyToMany
	@JoinTable(
			name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "roles_id"))
	private List<Roles> roles;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserDetails getUser_details() {
		return user_details;
	}

	public void setUser_details(UserDetails user_details) {
		this.user_details = user_details;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

	public User(long id, String name, UserDetails user_details, List<Address> address, List<Roles> roles) {
		super();
		this.id = id;
		this.name = name;
		this.user_details = user_details;
		this.address = address;
		this.roles = roles;
	}

	public User() {
		super();
	}
	
	
}
