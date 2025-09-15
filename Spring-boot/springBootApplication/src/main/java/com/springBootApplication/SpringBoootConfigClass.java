package com.springBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBoootConfigClass {


    @Bean("customer1")
    Customer customer(){
		Customer c1 = new Customer();
		c1.name = "sinchana";
		c1.age = 21;
		return c1;
	}


    @Bean("customer2")
    Customer customer2(){
		Customer c1 = new Customer();
		return c1;
	}

    @Bean //if you don't rovide teams name method name take as bean name
    Customer customer3(){
		Customer c1 = new Customer();
		return c1;
	}

}
