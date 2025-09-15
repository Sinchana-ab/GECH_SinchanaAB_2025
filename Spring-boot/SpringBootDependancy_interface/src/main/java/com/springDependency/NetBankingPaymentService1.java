package com.springDependency;

import org.springframework.stereotype.Component;

@Component("NBS1")
public class NetBankingPaymentService1  implements PaymentService{

	@Override
	public void paymentType() {
		System.out.println("net Patment service1 type");
	}
	
}
