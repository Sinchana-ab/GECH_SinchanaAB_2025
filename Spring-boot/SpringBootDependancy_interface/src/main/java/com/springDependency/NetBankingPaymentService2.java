package com.springDependency;

import org.springframework.stereotype.Component;

@Component("NBS2")
public class NetBankingPaymentService2 implements PaymentService{

	@Override
	public void paymentType() {
		System.out.println("Payment Service2 Type");
	}

}
