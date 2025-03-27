package com.predefinedFunctionalInterface;

import java.util.function.Predicate;

public class PreicateInJava {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//predicate holds condition i need to find the number is even or not
		Predicate<Integer> p = (a)->a%2 ==0;
		
		System.out.println(p.test(20));
		
	}

}
