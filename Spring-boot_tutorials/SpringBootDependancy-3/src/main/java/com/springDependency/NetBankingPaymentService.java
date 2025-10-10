package com.springDependency;

import org.springframework.stereotype.Component;

@Component
public class NetBankingPaymentService implements PaymentService {

	
	public NetBankingPaymentService() {
		System.out.println("NetBanking Object is created");
	}

	@Override
	public void paymentType() {
		System.out.println("net Banking service Type");
	}

}
