package com.springDependency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootDependancy3Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext	run = SpringApplication.run(SpringBootDependancy3Application.class, args);
		SpringBootInterface bean =	run.getBean(SpringBootInterface.class);
		bean.getPaymentService().paymentType();
		NetBankingPaymentService bean2 = run.getBean(NetBankingPaymentService.class);
		bean2.paymentType();
//		
//		 bean3 = run.getBean("NBS1");
//		bean3.paymentType();
//
//		NetBankingPaymentService bean2 = run.getBean(NetBankingPaymentService.class);
//		bean2.paymentType();

	}

}
