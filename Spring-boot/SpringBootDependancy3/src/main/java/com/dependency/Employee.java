package com.dependency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Employee {	
	private String name;
	private Address addressone;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddressone() {
		return addressone;
	}

	@Qualifier("address")
	@Autowired
	public void setAddressone(Address addressone) {
		this.addressone = addressone;
	}

	public Employee(String name, Address addressone) {
		super();
		this.name = name;
		this.addressone = addressone;
	}
	
	
}
