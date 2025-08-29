package functional_interface;

import java.time.LocalDate;
import java.util.function.Supplier;

public class SupplierInJava {
	public static void main(String[] args) {
		/*
		 * *it wont take any parameter it will return the value
		 * */
		Supplier<Double> sup =()->Math.random();
		Double res = sup.get();
		System.out.println(res);
		
		
		//print todays date
		System.out.println("Todays date: "+LocalDate.now());
		Supplier<LocalDate> sup1 = ()-> LocalDate.now();
		System.out.println("using supplier is "+sup1.get());
	}
}
