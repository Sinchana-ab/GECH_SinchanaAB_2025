package com.Form.Validation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="studentForms")
public class StudentValidations {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String  name;
	private int age;
	private String email;
	private String password;
	public StudentValidations(long id, String name, int age, String email,  String password) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.email = email;
		
		this.password= password;
	}
	public StudentValidations() {
		super();
	}
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
