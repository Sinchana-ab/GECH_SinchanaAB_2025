package com.dependency;

import org.springframework.stereotype.Component;

@Component
public class Address {
	private String name;

	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Address() {
		System.out.println("Address object is created ");
	}
	
}
