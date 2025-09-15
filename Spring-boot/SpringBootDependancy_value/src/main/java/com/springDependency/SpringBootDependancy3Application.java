package com.springDependency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootDependancy3Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext	run = SpringApplication.run(SpringBootDependancy3Application.class, args);
		
		Student s = run.getBean(Student.class);
		System.out.println("name is:  "+s.getName());
		System.out.println("Age is: "+s.getAge());
		System.out.println("Skills are: "+s.getSkills());
		System.out.println("Details are: "+s.getDetails());
		System.out.println("complete address: "+ s.getAddress().getPincode()+" state is: "+s.getAddress().getState());
	}

}
