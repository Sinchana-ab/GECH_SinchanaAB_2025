package com.springDependency;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Address {
	
	@Value("${address.state}")
	private String State;
	

	private int pincode;
	
	
	
	public String getState() {
		return State;
	}
	
	public void setState(String state) {
		State = state;
	}
	public int getPincode() {
		return pincode;
	}
	
	//single value or it will take everyparameter here only parameter
	@Value("${address.pincode}")
	public void setPincode(int pincode) {
		this.pincode = pincode;
	}
	public Address() {
		super();
	}
	
}
