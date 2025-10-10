package com.dependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class Employee {

	
	public String name;
	
	@Qualifier("address2")
	@Autowired
	public Address addressone;
	@Autowired
	public Address address4;
	public Employee() {
		System.out.println("employee object is created");
	}
	
	
}
