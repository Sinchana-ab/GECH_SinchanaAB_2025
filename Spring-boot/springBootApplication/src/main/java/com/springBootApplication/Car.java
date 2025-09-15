package com.springBootApplication;

import org.springframework.stereotype.Component;

@Component("car1")
public class Car {
	String name;
	String model;
	int no_w;
	public Car() {
		System.out.println("Car object is created");
	}
}
