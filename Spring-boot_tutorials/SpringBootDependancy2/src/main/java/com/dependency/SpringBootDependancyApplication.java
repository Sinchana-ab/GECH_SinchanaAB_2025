package com.dependency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootDependancyApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run =SpringApplication.run(SpringBootDependancyApplication.class, args);
		Employee e = (Employee) run.getBean("employee");
		System.out.println("employee bean addess: "+e);
		System.out.println("Employee address bean: "+(e.getAddressone()));
//		Address a = (Address) run.getBean("address");
//		System.out.println(a);
		
//		Address a2 = (Address) run.getBean("address3");
//		System.out.println(a2);
		Address a1 = (Address) run.getBean("address1");
		System.out.println("address  bean1: "+a1);
		
		Address a2 = (Address) run.getBean("address2");
		System.out.println("address bean2: "+a2);
	}

}
