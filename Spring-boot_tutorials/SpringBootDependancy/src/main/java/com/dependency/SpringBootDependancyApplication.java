package com.dependency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootDependancyApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run =SpringApplication.run(SpringBootDependancyApplication.class, args);
		Employee e = (Employee) run.getBean("employee");
		System.out.println(e);
		System.out.println(e.addressone);
		Address a = (Address) run.getBean("address");
		System.out.println(a);
		
//		Address a2 = (Address) run.getBean("address3");
//		System.out.println(a2);
		Address a1 = (Address) run.getBean("address1");
		System.out.println(a1);
		
		Address a2 = (Address) run.getBean("address2");
		System.out.println(a2);
	}

}
