package com.springDependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpringBootInterface {
	
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
