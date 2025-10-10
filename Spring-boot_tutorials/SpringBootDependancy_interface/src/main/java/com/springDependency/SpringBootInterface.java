package com.springDependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class SpringBootInterface {
	
	@Qualifier("NBS2")
	@Autowired
	public PaymentService paymentService;

	public PaymentService getPaymentService() {
		return paymentService;
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public SpringBootInterface() {
		super();
		System.out.println("SpringBootInteface object is created");
	}
	
}
