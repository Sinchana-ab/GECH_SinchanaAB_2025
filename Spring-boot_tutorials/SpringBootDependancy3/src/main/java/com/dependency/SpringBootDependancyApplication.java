package com.dependency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootDependancyApplication {

	///it will choosed based on naming convention and if bean not present ambiguity error will come
	public static void main(String[] args) {
		ConfigurableApplicationContext run =SpringApplication.run(SpringBootDependancyApplication.class, args);
		
	}

}
