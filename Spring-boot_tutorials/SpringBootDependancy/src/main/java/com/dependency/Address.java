package com.dependency;

import org.springframework.stereotype.Component;

@Component
public class Address {
	public String name;

	
	public Address() {
		System.out.println("Address object is created ");
	}
	
}
