package com.springDependency;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Student {
	
//	@Value("${student.name}")
	private String name;
	
//	@Value("32")
	private int age;
	
	@Autowired
	private Address address;
	
//	@Value("${student.skills}")
	private List<String> skills;

//	@Value("#{${student.details}}")
	private Map<String, String> details;
	
	
	@Autowired
	public Student(@Value("${student.name}") String name,@Value("32") int age, Address address,@Value("${student.skills}") List<String> skills,@Value("#{${student.details}}")  Map<String, String> details) {
	super();
	this.name = name;
	this.age = age;
	this.address = address;
	this.skills = skills;
	this.details = details;
}


	public Map<String, String> getDetails() {
		return details;
	}


	public void setDetails(Map<String, String> details) {
		this.details = details;
	}


	public List<String> getSkills() {
		return skills;
	}


	public void setSkills(List<String> skills) {
		this.skills = skills;
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


	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}
	
	

}
