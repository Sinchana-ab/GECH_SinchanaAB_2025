package com.collectionsInJava;

import java.util.ArrayList;

import java.util.List;

public class ListInJava {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		list.add(2);
		list.add("hello");
		list.add(10.55);
		System.out.println(list);
		String str= (String) list.get(1);
		double value = (double) list.get(2);
		System.out.println();
		
	}

}
