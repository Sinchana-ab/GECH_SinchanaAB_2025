package com.springBootApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootFirstApplication {

    private final Car car1;

    private final SpringBoootConfigClass springBoootConfigClass;

    private final Customer customer2;

    SpringBootFirstApplication(Customer customer2, SpringBoootConfigClass springBoootConfigClass, Car car1) {
        this.customer2 = customer2;
        this.springBoootConfigClass = springBoootConfigClass;
        this.car1 = car1;
    }

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(SpringBootFirstApplication.class, args);
		Object bean = run.getBean("customer1");
		Customer c1 = (Customer) bean;
		System.out.println(c1);//address
		System.out.println(c1.name);
		
		
		Customer customer2 = (Customer) run.getBean("customer2");
		System.out.println(customer2);
		
		Customer customer3 = (Customer) run.getBean("customer3");
		System.out.println(customer3);
		
//		  Customer bean2 = run.getBean(Customer.class); // use only ioc container contain only one object 
//		  System.out.println(bean2);
		  //new Customer()
		
		Car car = (Car) run.getBean("car1");
		System.out.println(car);
		System.out.println(car.name);
	}

}
