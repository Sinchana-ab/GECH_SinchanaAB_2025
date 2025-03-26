package com.collectionsInJava;

import java.util.ArrayList;

public class ArrayListDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * Arraylist
		 * Array list is a improved version of array(Dynamic Array)
		 * Array list underline the data structure is dynamic array
		 * Array listallow duplicate
		 * Array list will allow null value
		 * Array list preserve the order of insertion
		 */
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(null);
		System.out.println(list);
		System.out.println(list.get(3));
		System.out.println(list.get(6));
		System.out.println(list.size());
		list.remove(3);
		Integer i = 5;
		System.out.println(list);
		list.remove(i);

	}

}