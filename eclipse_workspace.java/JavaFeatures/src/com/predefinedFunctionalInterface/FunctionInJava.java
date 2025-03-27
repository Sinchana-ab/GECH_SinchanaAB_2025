package com.predefinedFunctionalInterface;

import java.util.function.Function;

public class FunctionInJava {

	public static void main(String[] args) {
		/*
		 * Function : it, t will take one parameter tj*/ 
		Function<String,Integer> f = (s)->s.length();
		int res = f.apply("sinchana");
		System.out.println(res);
		
		Function<Integer, Integer>f1 = (s)->s*s;
		int res1 = f1.apply(4);
		System.out.println(res1);
		}

}
