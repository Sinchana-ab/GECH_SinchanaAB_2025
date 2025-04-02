package com.dependencyInjection;

public class StartApplication {
	public static void main(String[] args) {
		Address address = new Address("Hassan", "karnataka");
		//1. constructor injection
		Student student1 = new Student(10, "Sinchana", address);
		System.out.println(student1.getId());
		System.out.println(student1.getName());
		Address address1 = student1.getAddress();
		System.out.println(address1.getCity());
		System.out.println(address1.getState());
		System.out.println(address.getCity());
		System.out.println(address.getState());
		
		//2. setter injection
		Address address2 = new Address();
		address2.setCity("Mangaluru");
		address2.setState("karnataka");
		Student student2 = new Student();
		student2.setId(20);
		student2.setName("Nayana");
		student2.setAddress(address2);
		System.out.println(student2.getId());
		System.out.println(student2.getName());
		Address address3 = student2.getAddress();
		System.out.println(address3.getCity());
		System.out.println(address3.getState());
	}
}
