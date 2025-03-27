package com.predefinedFunctionalInterface;

import java.util.function.Consumer;

public class ConsumerInJava {

	public static void main(String[] args) {
		/*
		 * Consumer
		 * it will consume wont return anything
		 * */
		Consumer<String> c = (s)->System.out.println("string "+s+" length: "+s.length());
		c.accept("Sinchana");
		//Consumer<String> d = (sinchana)->System.out.println("sinchana is: "+sinchana);
		//System.out.println(c.andThen(d));
	}

}
