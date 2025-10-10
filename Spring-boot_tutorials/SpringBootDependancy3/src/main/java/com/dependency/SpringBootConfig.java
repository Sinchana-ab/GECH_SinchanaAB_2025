package com.dependency;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringBootConfig {
	@Bean("address1")
	public Address address1() {
		return new Address("hassan");
	}
	
	@Bean("address2")
	public Address address2() {
		return new Address("bangoaluru");
	}
}
