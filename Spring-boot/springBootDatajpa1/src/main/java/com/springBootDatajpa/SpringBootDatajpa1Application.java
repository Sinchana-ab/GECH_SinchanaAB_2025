package com.springBootDatajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootDatajpa1Application {

   
	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(SpringBootDatajpa1Application.class, args);
		SpringData s1 = run.getBean(SpringData.class);
		System.out.println("student Details");
		s1.addStudent();
		s1.getStudent();
		s1.getStudentByEmail();
	}
	
}
