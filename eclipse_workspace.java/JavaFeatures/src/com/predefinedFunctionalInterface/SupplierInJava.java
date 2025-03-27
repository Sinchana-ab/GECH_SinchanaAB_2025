package com.predefinedFunctionalInterface;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.function.Supplier;

public class SupplierInJava {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Supplier<Date> s = ()->new Date();
		System.out.println("current date and time is: "+s.get());
	}
	

}
