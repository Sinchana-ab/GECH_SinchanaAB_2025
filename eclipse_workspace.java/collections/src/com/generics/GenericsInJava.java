package com.generics;

import java.util.ArrayList;
import java.util.List;

public class GenericsInJava {

	public static void main(String[] args) {
		/*
		 * ------Generic------
		 * to provide the type safety and to avoid the typw casting in collection then we should go for generic
		 *  
		 *  without doing explicitly without converting datatype we can use */
		char [] arr = {'a','b','c'};
		char firstele = arr[0];
		System.out.println(firstele);
		
		List<Integer> list = new ArrayList<Integer>();//type safety
		list.add(12);
		list.add(120);
		//list.add(10.67);
		int a = list.get(1);
		System.out.println(a);
		System.out.println(list);
		
	}

}
